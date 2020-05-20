<template>

  <codemirror
    ref="cmEditor"
    v-model="code"
    :options="cmOptions"
    @ready="onCmReady"
    @focus="onCmFocus"
    @input="onCmCodeChange"
    @cursorActivity="onCmCursorActivity"
  />

</template>

<script>
import { codemirror } from 'vue-codemirror'

export default {
  name: 'VueCodeMirror',
  components: { codemirror },
  props: {
    code: String,
    cmOptions: Object
  },
  computed: {
    codemirror() {
      return this.$refs.cmEditor.codemirror
    }
  },
  mounted() {
    console.log('the current CodeMirror instance object:', this.codemirror)
    // you can use this.codemirror to do something...
  },
  methods: {
    onCmCursorActivity(cm) {
      console.log('the editor is readied!', cm)
    },
    onCmReady(cm) {
      console.log('the editor is readied!', cm)
    },
    onCmFocus(cm) {
      console.log('the editor is focused!', cm)
    },
    onCmCodeChange(newCode) {
      console.log('this is new code', newCode)
      this.code = newCode
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
</style>
