<template>
  <div class="app-container">
    <!--重新设计 tableInfo columnInfo DictValue 让其支持可新增即可-->
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <!--展开行-->
      <el-table-column type="expand">
        <template slot-scope="{row}">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="是否需要界面控件">
              <el-radio-group v-model="row.ui">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
            <!--部门名称-->
            <el-form-item label="部门编码">
              <el-input
                v-model="row.name"
                placeholder="请输入部门编码"
                clearable
                size="small"
              />
            </el-form-item>
            <!--部门名称-->
            <el-form-item label="部门名称">
              <el-input
                v-model="row.name"
                placeholder="请输入部门名称"
                clearable
                size="small"
              />
            </el-form-item>
            <!--状态-->
            <el-form-item label="状态">
              <el-select v-model="row.ui" placeholder="部门状态" clearable size="small">
                <el-option
                  v-for="dict in [{key:1,value:'数字'},{key:2,value:'字符串'}]"
                  :key="dict.key"
                  :label="dict.value"
                  :value="dict.key"
                />
              </el-select>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="字段名称"
        prop="name"
      >
        <template slot-scope="{row}">
          <el-input v-if="row.edit" v-model="row.name" type="text" class="edit-input" size="small" />
          <span v-else>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="字段别名"
        prop="alias"
      >
        <template slot-scope="{row}">
          <el-input v-if="row.edit" v-model="row.alias" type="text" class="edit-input" size="small" />
          <span v-else>{{ row.alias }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="字段类型"
        prop="type"
      />
      <el-table-column
        label="长度"
        prop="length"
      >
        <template slot-scope="{row}">
          <el-input v-if="row.edit" v-model="row.length" type="number" class="edit-input" size="small" />
          <span v-else>{{ row.length }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="{row}">
          <el-button
            v-if="row.edit"
            type="success"
            size="small"
            icon="el-icon-circle-check-outline"
            @click="row.edit=false"
          >
            完成
          </el-button>
          <el-button
            v-else
            type="primary"
            size="small"
            icon="el-icon-edit"
            @click="row.edit=true"
          >
            编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <dev />
  </div>
</template>

<script>
import Dev from '@/views/error-page/Dev'
export default {
  name: 'Gen',
  components: { Dev },
  data() {
    return {
      tableData: [{
        edit: true,
        name: 'name',
        alias: '名称',
        type: '江浙小吃、小吃零食',
        length: 255,
        ui: 1
      }]
    }
  }
}
</script>

<style lang="scss" scoped>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
