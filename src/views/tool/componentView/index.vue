<template>
  <div class="app-container">
    <component-select :type="'all'"></component-select>
  </div>
</template>
<script>
// 只能有一处引用 ComponentView 组件, 当Menu页面引用 ComponentView 组件后, 此页面也打不开
import ComponentSelect from '@/components/ComponentView'
export default {
  name: 'Test',
  components: { ComponentSelect }
}
</script>
