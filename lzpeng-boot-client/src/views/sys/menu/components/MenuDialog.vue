<template>
  <div>
    <!-- 添加或修改菜单对话框 -->
    <el-dialog v-el-drag-dialog :title="dialog.title" :visible.sync="dialog.show" width="600px" @close="closeDialog">
      <el-form ref="form" :model="dialog.form" :rules="rules" label-width="80px">
        <el-row>
          <!--父级菜单-->
          <el-col :span="24">
            <el-form-item label="上级菜单">
              <treeselect
                v-model="dialog.form.parentId"
                :options="menuOptions"
                :normalizer="normalizerMenuOptions"
                :show-count="true"
                placeholder="选择上级菜单"
                @input="changeParent"
              />
            </el-form-item>
          </el-col>
          <!--目录的下级只可以是目录和菜单 菜单的下级只能是按钮 -->
          <el-col :span="24">
            <el-form-item label="菜单类型" prop="type">
              <el-radio-group ref="menuType" v-model="dialog.form.type">
                <el-radio
                  v-for="dict in dialog.dictValues['type']"
                  :key="dict.key"
                  :label="dict.key"
                  :disabled="showType(dict.key)"
                >{{ dict.value }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <!--菜单名称-->
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="title">
              <el-input v-model="dialog.form.title" placeholder="请输入菜单名称" />
            </el-form-item>
          </el-col>
          <!--顺序号-->
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="dialog.form.orderNum" controls-position="right" :min="0" disabled />
            </el-form-item>
          </el-col>
          <!--菜单状态-->
          <el-col :span="12">
            <el-form-item v-if="dialog.form.type != '2'" label="菜单状态">
              <el-radio-group v-model="dialog.form.hidden">
                <el-radio
                  v-for="dict in dialog.dictValues['hidden']"
                  :key="dict.key"
                  :label="dict.key"
                >{{ dict.value }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <!--路由地址-->
          <el-col :span="12">
            <el-form-item v-if="dialog.form.type != '2'" label="路由地址" prop="path">
              <el-input v-model="dialog.form.path" placeholder="请输入路由地址" />
            </el-form-item>
          </el-col>
          <!--菜单图标-->
          <el-col :span="24">
            <el-form-item v-if="dialog.form.type != '2'" label="菜单图标">
              <el-popover
                placement="bottom-start"
                width="460"
                trigger="click"
                @show="$refs['iconSelect'].reset()"
              >
                <icon-select ref="iconSelect" @selected="selectedIcon" />
                <el-input slot="reference" v-model="dialog.form.icon" placeholder="点击选择图标" readonly>
                  <svg-icon
                    v-if="dialog.form.icon"
                    slot="prefix"
                    :icon-class="dialog.form.icon"
                    class="el-input__icon"
                    style="height: 32px;width: 16px;"
                  />
                  <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                </el-input>
              </el-popover>
            </el-form-item>
          </el-col>
          <!---->
          <el-col v-if="dialog.form.type != '2'" :span="24">
            <!--组件路径-->
            <el-form-item v-if="dialog.form.frame == '0'" label="组件路径" prop="component">
              <treeselect
                v-model="dialog.form.component"
                :options="componentOptions"
                :disable-branch-nodes="true"
                :normalizer="normalizerComponentOptions"
                :show-count="true"
                placeholder="选择组件"
              >
                <div slot="value-label" slot-scope="{ node }">{{ node.id }}</div>
              </treeselect>
            </el-form-item>
            <!--外链路径-->
            <el-form-item v-else label="外链路径" prop="component">
              <el-input v-model="dialog.form.component" placeholder="请输入外链路径" />
            </el-form-item>
          </el-col>
          <!--是否外链-->
          <el-col :span="12">
            <el-form-item v-if="dialog.form.type == '1'" label="是否外链">
              <el-radio-group v-model="dialog.form.frame">
                <el-radio
                  v-for="dict in dialog.dictValues['frame']"
                  :key="dict.key"
                  :label="dict.key"
                >{{ dict.value }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <!--权限标识-->
          <el-col :span="12">
            <el-form-item v-if="dialog.form.type != '0'" label="权限标识">
              <el-input v-model="dialog.form.number" placeholder="权限标识" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!--确定 取消按钮-->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="closeDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import elDragDialog from '@/directive/el-drag-dialog' // 可拖拽Dialog
import { getAllRouters, insertMenu, updateMenu } from '@/api/sys/menu' // 菜单API
import Treeselect from '@riophae/vue-treeselect' // 树形结构选择器 Select
import '@riophae/vue-treeselect/dist/vue-treeselect.css' // 树形结构选择器样式
import IconSelect from '@/components/IconSelect' // 图标选择器
import { allComponentPath } from '@/utils/component-scan' // 导入所有组件路径 组件路径选择器使用
export default {
  name: 'MenuDialog', // 组件名称
  components: {
    Treeselect, IconSelect // 注册树形结构选择器和图标选择器组件
  },
  directives: { elDragDialog }, // 注册可拖拽Dialog directive
  props: {
    dialog: Object // 从父组件接收的参数
  },
  data() {
    return {
      // 父菜单是叶子还是分支
      parentMenu: {},
      // 菜单树
      menuOptions: [],
      // 组件树
      componentOptions: [],
      // 表单校验
      rules: {
        title: [
          { required: true, message: '菜单名称不能为空', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '菜单顺序不能为空', trigger: 'blur' }
        ],
        path: [
          { required: true, message: '路由地址不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  // 初始化组件时执行
  created() {
    // 获取菜单树
    this.getMenuTreeselect()
    // 获取组件树
    this.getComponentTreeselect()
  },
  methods: {
    // 选择图标
    selectedIcon(name) {
      this.dialog.form.icon = name
    },
    // 关闭Dialog
    closeDialog() {
      // vue子组件修改父组件传递过来的值 https://www.cnblogs.com/Sky-Ice/p/10456533.html
      // this.open = false // 此句错误
      // 不要去改变父组件传递过来的值，会发生意想不到的问题
      // 应使用$emit由父组件来改变值
      // this.$emit('closed')
      // 或者父传递对象或数组，不要传递基本数据类型
      this.dialog.form = this.deepClone(this.dialog.defaultForm)
      this.resetForm('form')
      this.dialog.show = false
    },
    // 表单提交
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          const model = this.dialog.form
          if (+model.parentId === 0) {
            // 0 代表没有父菜单
            model.parentId = null
          }
          // name 不能为 null 会导致菜单不显示在 TagsView
          model.name = model.name || model.path
          if (model.id) {
            updateMenu(model.id, this.filterModel(model)).then(res => {
              this.$notify({
                title: '成功',
                message: '修改菜单成功',
                type: 'success'
              })
              this.closeDialog()
              this.$emit('refresh')
              this.getMenuTreeselect()
            })
          } else {
            insertMenu(this.filterModel(model)).then(res => {
              this.$notify({
                title: '成功',
                message: '新增菜单成功',
                type: 'success'
              })
              this.closeDialog()
              this.$emit('refresh')
              this.getMenuTreeselect()
            })
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    /** 查询菜单下拉树结构 */
    getMenuTreeselect() {
      getAllRouters().then(res => {
        this.menuOptions = []
        const menu = { id: 0, title: '主类目', children: res }
        this.menuOptions.push(menu)
      })
    },
    /** 查询组件下拉树结构 */
    getComponentTreeselect() {
      const json = this.convertStringToTree(allComponentPath, '/')
      const tree = this.jsonToTreeSelectData(json)
      this.componentOptions = tree
    },
    /** 转换菜单数据结构 */
    normalizerMenuOptions(node) {
      // {key:show} 可以选择的key 是否显示
      // 将该id是叶子节点还是分支节点存入变量
      if (node.type === 1) {
        // 是菜单
        this.parentMenu[node.id] = { 0: false, 1: false, 2: true } // 'Leaf'
        delete node.children
      } else if (!node.type || node.type === 0) {
        // 是目录
        this.parentMenu[node.id] = { 0: true, 1: true, 2: false }// 'Branch'
      }
      return {
        id: node.id,
        label: node.title,
        children: node.children
      }
    },
    /** 转换组件数据结构 */
    normalizerComponentOptions(node) {
      // :disable-branch-nodes=true 禁用分支节点只可以选择叶子节点
      // 最终数据库存的路径格式
      const suffix = '.vue'
      const formatComponentPath = node.id.replace(suffix, '').replace('src', '@')
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: formatComponentPath,
        label: node.label,
        children: node.children
      }
    },
    /**
     * 获取 type 是否显示
     * @param type
     */
    showType(type) {
      var parentId = this.dialog.form.parentId
      // 根据当前选择的父菜单判断显示哪些option
      return this.parentMenu[parentId] && !this.parentMenu[parentId][type]
    },
    // 当父级菜单更新时 动态更新本菜单类型
    changeParent(value) {
      if (!this.parentMenu[value] || !value) return // 如果没有找到对应的key或者key为undefined直接返回,点击列表查询条件后的新增时 value 为undefined
      const curType = this.dialog.form.type // 当前选中的菜单类型
      if (!this.parentMenu[value][curType]) { // 如果当前选择的菜单类型不可以在当前选择的父菜单下
        const optionStatus = this.parentMenu[value] // 所有可供选择的键和其是否可以被选
        const keys = Object.keys(this.parentMenu[value]) // 所有可供选择的键 字符串
        const key = keys.find(key => optionStatus[key]) // 获取可供选择的第一个键 字符串
        this.dialog.form.type = +key // 更新select选择的项 需要转为数字
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
