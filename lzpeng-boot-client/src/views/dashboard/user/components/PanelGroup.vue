<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleClickPanel('notices')">
        <div class="card-panel-icon-wrapper icon-notice">
          <svg-icon icon-class="message" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            通知
          </div>
          <count-to :start-val="notice.startVal" :end-val="notice.endVal" :duration="notice.duration" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleClickPanel('todoList')">
        <div class="card-panel-icon-wrapper icon-todo-list">
          <svg-icon icon-class="list" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            待办
          </div>
          <count-to :start-val="0" :end-val="13600" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'
import { count, noticeList } from '@/api/home/dashboard' // 首页api

export default {
  name: 'PanelGroup',
  components: {
    CountTo
  },
  data() {
    return {
      // 通知计数
      notice: {
        startVal: 0,
        endVal: 0,
        duration: 3600
      },
      // 待办计数
      todoList: {
        startVal: 0,
        endVal: 0,
        duration: 3600
      }
    }
  },
  created() {
    count().then(res => {
      if (res.noticeCount !== 0) {
        this.notice.endVal = res.noticeCount
      }
      if (res.todoListCount !== 0) {
        this.todoList.endVal = res.todoListCount
      }
    })
  },
  methods: {
    handleClickPanel(type) {
      this.$emit('handleClickPanel', type)
    }
  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-notice {
        background: #36a3f7;
      }

      .icon-todo-list {
        background: #34bfa3;
      }
    }

    .icon-message {
      color: #36a3f7;
    }
    .icon-todo-list {
      color: #34bfa3;
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
