// language
import 'codemirror/lib/codemirror.js'
import 'codemirror/mode/javascript/javascript.js'

// theme css
import 'codemirror/theme/idea.css'
import 'codemirror/lib/codemirror.css'

// require active-line.js
import 'codemirror/addon/selection/active-line.js'

// styleSelectedText
import 'codemirror/addon/selection/mark-selection.js'
import 'codemirror/addon/search/searchcursor.js'

// hint
import 'codemirror/addon/hint/show-hint.js'
import 'codemirror/addon/hint/show-hint.css'
import 'codemirror/addon/hint/javascript-hint.js'
import 'codemirror/addon/selection/active-line.js'

// highlightSelectionMatches
import 'codemirror/addon/scroll/annotatescrollbar.js'
import 'codemirror/addon/search/matchesonscrollbar.js'
import 'codemirror/addon/search/searchcursor.js'
import 'codemirror/addon/search/match-highlighter.js'

// keyMap
import 'codemirror/mode/clike/clike.js'
import 'codemirror/addon/edit/matchbrackets.js'
import 'codemirror/addon/comment/comment.js'
import 'codemirror/addon/dialog/dialog.js'
import 'codemirror/addon/dialog/dialog.css'
import 'codemirror/addon/search/searchcursor.js'
import 'codemirror/addon/search/search.js'
import 'codemirror/keymap/sublime.js'

// foldGutter
import 'codemirror/addon/fold/foldgutter.css'
import 'codemirror/addon/fold/brace-fold.js'
import 'codemirror/addon/fold/comment-fold.js'
import 'codemirror/addon/fold/foldcode.js'
import 'codemirror/addon/fold/foldgutter.js'
import 'codemirror/addon/fold/indent-fold.js'
import 'codemirror/addon/fold/markdown-fold.js'
import 'codemirror/addon/fold/xml-fold.js'

// @link {https://blog.csdn.net/weixin_43080277/article/details/83860629}
export const cmOptions = {

  indentWithTabs: true, // 在缩进时，是否tabSize 应该用N个制表符替换前N *个空格。默认值为false

  smartIndent: true, // 是否使用模式提供的上下文相关缩进（或者只是缩进与之前的行相同）。默认为true。

  lineNumbers: true, // 是否在编辑器左侧显示行号。

  matchBrackets: true,

  styleActiveLine: true,

  cursorHeight: 1, // 光标高度

  autoRefresh: true,

  tabSize: 4,
  styleSelectedText: false,
  line: true,
  foldGutter: true,
  gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
  highlightSelectionMatches: { showToken: /\w/, annotateScrollbar: true },
  // hint.js options
  hintOptions: {
    completeSingle: false
  },
  keyMap: 'sublime',
  showCursorWhenSelecting: true,
  theme: 'idea',
  extraKeys: { 'Alt-/': 'autocomplete' }
}

