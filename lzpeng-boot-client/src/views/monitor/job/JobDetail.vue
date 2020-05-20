<template>

  <div class="app-container">
    <sticky :class-name="'sub-navbar'">
      <el-button @click="submitForm">保存</el-button>
      <el-button type="success" @click="startJob" v-if="isBillId(form.id) && +form.jobStatus === 0">启用</el-button>
      <el-button type="danger" @click="stopJob" v-if="isBillId(form.id) && +form.jobStatus !== 0">停止</el-button>
      <el-button type="warning" @click="pauseJob" v-if="isBillId(form.id) && +form.jobStatus === 1">暂停</el-button>
      <el-button type="warning" @click="resumeJob" v-if="isBillId(form.id) && +form.jobStatus === 2">恢复</el-button>
      <el-button type="success" @click="executeJob" v-if="isBillId(form.id)">执行一次</el-button>
    </sticky>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" style="margin-top: 20px;" :disabled="+form.jobStatus !== 0">
      <el-row>
        <el-col :span="12">
          <el-form-item label="任务编码" prop="number">
            <el-input v-model="form.number" placeholder="请输入任务编码" :disabled="isBillId(form.id)" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任务名称" prop="name" >
            <el-input v-model="form.name" placeholder="请输入任务名称" :disabled="isBillId(form.id)" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="分组名称" prop="group">
            <el-input v-model="form.group" placeholder="请输入分组名称" :disabled="isBillId(form.id)" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任务计划" prop="cron">
            <!--<span slot="label">-->
                <!--任务计划-->
                <!--<el-tooltip placement="top">-->
                  <!--<div slot="content">-->
                    <!--任务计划为cron表达式-->
                    <!--<br />-->
                    <!--<a href="#">cron表达式参考</a>-->
                  <!--</div>-->
                  <!--<i class="el-icon-question"></i>-->
                <!--</el-tooltip>-->
              <!--</span>-->
            <el-input v-model="form.cron" placeholder="任务计划" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="任务类型" prop="type">
            <el-radio-group v-model="form.type" :disabled="isBillId(form.id)">
              <el-radio
                v-for="dict in dictValues['type']"
                :key="dict.key"
                :label="dict.key"
              >{{ dict.value }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="任务状态" prop="jobStatus">
            <span>{{dictValues['jobStatus'] ? dictValues['jobStatus'][+form.jobStatus].value : ''}}</span>
          </el-form-item>
        </el-col>
        <el-col v-if="form.type === 0" :span="12">
          <el-form-item label="类名" prop="clazz" :disabled="isBillId(form.id)">
            <el-input v-model="form.clazz" placeholder="请输入类名" />
          </el-form-item>
        </el-col>
        <el-col v-else-if="form.type === 1" :span="12">
          <el-form-item>
            <a
              href="https://developer.mozilla.org/zh-CN/docs/Mozilla/Projects/Rhino/Scripting_Java"
              target="_blank"
            >Rhino 脚本参考
            </a>
          </el-form-item>
        </el-col>
        <el-col v-else-if="form.type === 2" :span="12">
          <el-form-item>
            <a href="https://www.jianshu.com/p/467aaf5254f8" target="_blank">Nashorn 脚本参考</a>
          </el-form-item>
        </el-col>
      </el-row>
      <div v-if="form.type !== 0">
        <codemirror
          :code="form.script"
          :options="cmOptions"
          @ready="onCmReady"
          @focus="onCmFocus"
          @input="onCmCodeChange"
        />
      </div>
    </el-form>
  </div>

</template>

<script>
import dedent from 'dedent'
import { codemirror } from 'vue-codemirror'
import { cmOptions } from '@/utils/codemirror'
import Sticky from '@/components/Sticky/index'
import { getJobDict, insertJob, updateJob, getJobById, stopJob, startJob, pauseJob, executeJob, resumeJob } from '@/api/monitor/job' // 定时任务api

export default {
  name: 'JobDetail',
  components: { Sticky, codemirror },
  data() {
    return {
      code: dedent`
      // Alt + / 提示代码
      // 内置变量 log: 打印后台日志,类型 org.slf4j.Logger
      // 内置变量 jobKey: 定时任务标识,类型 org.quartz.JobKey
      // 内置变量 jobDetail: 定时任务对象,类型 org.quartz.JobDetail
      // 内置变量 jobDataMap: 定时任务参数,类型 org.quartz.JobDataMap
      // 内置变量 jobExecutionContext: 定时任务执行时的上下文,类型 org.quartz.JobExecutionContext
      // 内置变量 jobDataMap.getString("script"): 定时任务执行的脚本,类型 java.lang.String
      // 内置变量 jobDataMap.get("jobId"): 定时任务Id,类型 java.lang.String
      var imp = JavaImporter(
          Packages.java.lang,
          Packages.java.util,
          Packages.cn.hutool.extra.spring
      );
      with (imp) {
          // 可以自定义函数
          function getBean(beanName){
              return SpringUtil.getBean(beanName);
          }
          var jobService = getBean("jobService"); // 获取Bean
          var jobId = jobDataMap.getString("jobId"); // 获取当前定时任务id
          var job = jobService.findById(jobId); // 获取当前定时任务配置
          log.info("jobType: {}", job.getType()); // 获取当前定时任务类型
          log.info("jobKey: {}", jobKey); // 获取当前定时任务标识
          log.info("jobService: {}", jobService.getClass()); // Bean jobService
          log.info("jobExecutionContext: {}", jobExecutionContext); // 获取当前定时任务上下文
      }
      `,
      cmOptions: cmOptions,
      // 定时任务数据字典
      jobDict: {},
      // 缓存每列的详细信息 {fieldName:columnInfo}
      columnInfo: {},
      // 缓存每列的数据字典 {fieldName:dictValues}
      dictValues: {},
      form: {
        enabled: 0,
        type: 0
      }, // 当前单据信息
      // 表单校验
      rules: {
        name: [
          { required: true, message: '任务名称不能为空', trigger: 'blur' }
        ],
        number: [
          { required: true, message: '任务编码不能为空', trigger: 'blur' }
        ],
        group: [
          { required: true, message: '任务分组不能为空', trigger: 'blur' }
        ],
        cron: [
          { required: true, message: '任务计划不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    console.log(this.$route.params)
  },
  async created() {
    // 获取或初始化单据信息
    await this.handleModel()
    // 获取数据字典
    await this.handleDict()
    this.cmOptions.mode = 'text/javascript'
  },
  methods: {
    onCmReady(cm) {
      // cm 启动完成
      console.log('the editor is readied!', cm)
    },
    onCmFocus(cm) {
      // 获得焦点
      console.log('the editor is focused!', cm)
    },
    onCmCodeChange(newCode) {
      // 编辑器文本改变
      console.log('this is new code', newCode)
      this.code = newCode
      this.form.script = newCode
    },

    /**
     * 获取或初始化单据信息
     */
    async handleModel() {
      this.form.id = this.$route.params.id
      if (this.isBillId(this.form.id)) {
        this.form = await getJobById(this.form.id)
        this.form.enabled = +this.form.enabled
        this.form.type = +this.form.type
        this.form.jobStatus = +this.form.jobStatus
      } else {
        this.form.script = this.code
        this.form.type = 0
        this.form.jobStatus = 0
        const now = new Date()
        this.form.cron = `${now.getSeconds()} ${now.getMinutes()} ${now.getHours()} * * ?`
      }
    },
    /**
     * 获取数据字典
     */
    async handleDict() {
      // 获得本单据的详细定义信息
      const dict = await getJobDict()
      this.jobDict = dict // 本单据的详细定义信息
      const result = this.parseTableMeta(dict) // 解析本单据的详细定义信息
      this.columnInfo = result.columnInfo // 每列的详细信息
      this.dictValues = result.dictValues // 每列的数据字典
    },
    // 表单提交
    submitForm() {
      if (this.form.jobStatus !== 0) {
        this.$alert('只有未启用状态的任务才可编辑哦', '提示', {
          confirmButtonText: '确定',
          callback: action => {
          }
        })
        return
      }
      this.$refs['form'].validate((valid) => {
        if (valid) {
          const model = this.form
          if (this.isBillId(model.id)) {
            updateJob(model.id, this.filterModel(model)).then(res => {
              this.form = res
              this.form.enabled = +res.enabled
              this.form.type = +res.type
              this.$notify({
                title: '成功',
                message: '修改定时任务成功',
                type: 'success'
              })
            })
          } else {
            delete model.id
            insertJob(this.filterModel(model)).then(res => {
              this.form = res
              this.form.enabled = +res.enabled
              this.form.type = +res.type
              this.$notify({
                title: '成功',
                message: '新增定时任务成功',
                type: 'success'
              })
            })
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 启动任务
    startJob() {
      startJob(this.form.id).then(res => {
        this.$notify({
          title: '成功',
          message: '启动任务成功',
          type: 'success'
        })
        this.handleModel()
      })
    },
    // 停止任务
    stopJob() {
      stopJob(this.form.id).then(res => {
        this.$notify({
          title: '成功',
          message: '停止任务成功',
          type: 'success'
        })
        this.handleModel()
      })
    },
    // 暂停任务
    pauseJob() {
      pauseJob(this.form.id).then(res => {
        this.$notify({
          title: '成功',
          message: '暂停任务成功',
          type: 'success'
        })
        this.handleModel()
      })
    },
    // 恢复任务
    resumeJob() {
      resumeJob(this.form.id).then(res => {
        this.$notify({
          title: '成功',
          message: '恢复任务成功',
          type: 'success'
        })
        this.handleModel()
      })
    },
    // 执行任务
    executeJob() {
      executeJob(this.form.id).then(res => {
        this.$notify({
          title: '成功',
          message: '执行任务成功',
          type: 'success'
        })
      })
    }
  }
}
</script>

<style>
  .CodeMirror-focused .cm-matchhighlight {
    background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFklEQVQI12NgYGBgkKzc8x9CMDAwAAAmhwSbidEoSQAAAABJRU5ErkJggg==);
    background-position: bottom;
    background-repeat: repeat-x;
  }

  .cm-matchhighlight {
    background-color: lightgreen;
  }

  .CodeMirror-selection-highlight-scrollbar {
    background-color: green;
  }

  .CodeMirror {
    min-height: 300px;
    height: auto;
  }
</style>

