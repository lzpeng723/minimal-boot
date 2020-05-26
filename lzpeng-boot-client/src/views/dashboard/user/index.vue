<template>
  <div class="dashboard-editor-container">
    <github-corner class="github-corner" />
    <!--通知 待办-->
    <panel-group @handleClickPanel="handleClickPanel" />
    <ul>
      <li v-for="item in noticeList" :key="item.id">
        <div>
          <span>{{item.notice.name}}</span>
        </div>
      </li>
    </ul>

  </div>
</template>

<script>
import GithubCorner from '@/components/GithubCorner'
import PanelGroup from './components/PanelGroup'
import { noticeList } from '@/api/home/dashboard' // 首页api

export default {
  name: 'DashboardUser',
  components: {
    GithubCorner,
    PanelGroup
  },
  data() {
    return {
      noticeList: []
    }
  },
  methods: {
    handleClickPanel(type) {
      switch (type) {
        case 'notices':
          if (this.noticeList.length > 0) {
            this.noticeList = []
          } else {
            noticeList().then(res => {
              this.noticeList = res
            })
          }
          break
        case 'todoList':
          console.log(type)
          break
        default:
          console.log(type)
          break
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
