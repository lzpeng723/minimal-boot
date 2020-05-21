// sql语句处理工具
export default {
  mergeFilter: function(subjects, joins) {
    let result = '{"subject":['
    let sum = 0
    for (let i = 0; i < subjects.length; i++) {
      if (subjects[i].indexOf('subject') > 0) {
        result += subjects[i]
        sum++
      } else if (subjects[i].length > 0) {
        result += ('{"queryID":"' + subjects[i] + '"}')
        sum++
      } else {
        continue
      }
      if (i < subjects.length - 1) {
        result += ','
      }
    }
    const link = joins.split('#')
    let mask = '#0'
    if (link.length === 1) {
      for (let j = 1; j < sum; j++) {
        mask += (joins + ('#' + j))
      }
    } else {
      mask = joins
    }
    result += '],"mask":"' + mask + '"}'
    return result
  },

  getFilter: function(keys, links, values, joins) {
    const get = function(key, link, value) {
      let tempFilter = ''
      if (link.indexOf('in') > -1 || link.indexOf('between') > -1) {
        if (value instanceof Array) {
          switch (typeof (value[0])) {
            case 'number':
            case 'boolean':
              tempFilter = '{"subject":[{"left":"' + key + '","link":"' + link + '","right":[' + value + ']}],"mask":"#0"}'
              break
            case 'string':
              var r = '"'
              for (var i in value) {
                r += value[i] + '"'
                if (i < value.length - 1) {
                  r += ',"'
                }
              }
              tempFilter = '{"subject":[{"left":"' + key + '","link":"' + link + '","right":[' + r + ']}],"mask":"#0"}'
              break
            default:
              tempFilter = '{"subject":[{"left":"' + key + '","link":"' + link + '","right":[""]}],"mask":"#0"}'
          }
        }
      } else {
        switch (typeof (value)) {
          case 'number':
          case 'boolean':
            tempFilter = '{"subject":[{"left":"' + key + '","link":"' + link + '","right":' + value + '}],"mask":"#0"}'
            break
          case 'string':
          case 'object':
            tempFilter = '{"subject":[{"left":"' + key + '","link":"' + link + '","right":"' + value + '"}],"mask":"#0"}'
            break
          default:
            tempFilter = '{"subject":[{"left":"' + key + '","link":"' + link + '","right":""}],"mask":"#0"}'
        }
      }
      return tempFilter
    }
    let filter = ''
    if (links.indexOf('in') < 0 && links.indexOf('between') < 0 && values instanceof Array) {
      const i = values.length
      if (i === 1) {
        filter = get(keys[0], links[0], values[0])
      } else {
        const subjects = []
        for (var j = 0; j < i; j++) {
          if (keys instanceof Array) {
            subjects[j] = get(keys[j], links[j] ? links[j] : links[0], values[j])
          } else {
            subjects[j] = get(keys, links[j] ? links[j] : links[0], values[j])
          }
        }
        filter = this.mergeFilter(subjects, joins)
      }
    } else {
      filter = get(keys, links, values)
    }
    return filter
  },
  // 将传统filter转换为约定格式
  transFilter: function(filter) {
    const parseExpr = this._parseFilter(filter)
    return this._assembleFilter(parseExpr)
  },
  // 修改sql中的某个条件，filter为原条件，args1和args2均为数组
  // args1长度小于等于3，分别为left（必传）和link，right（非必传）
  // args2为需要替换的条件，长度小于等于3，如果维持不变可在对应位置传undefined，其他均会替换
  // 为避免影响原filter，采用返回新fiter，使用需自行获取返回值
  replaceFilter: function(filter, args1, args2) {
    const replace = function(f, args1, args2) {
      if (f.subject) {
        for (var i = 0; i < f.subject.length; i++) {
          if (replace(f.subject[i], args1, args2)) {
            return
          }
        }
      } else {
        if (f.left === args1[0] && (!args1[1] || f.link === args1[1]) && (args1[2] === undefined || f.right === args1[2])) {
          f.left = args2[0] === undefined ? f.left : args2[0]
          f.link = args2[1] === undefined ? f.link : args2[1]
          f.right = args2[2] === undefined ? f.right : args2[2]
          return true
        } else {
          return false
        }
      }
    }
    const param = JSON.parse(filter)
    replace(param, args1, args2)
    return JSON.stringify(param)
  },
  // 获取某个条件对应的值，args为数组，第一个参数为left（必传），第二个参数为link（若不传则默认匹配第一个left符合的）
  getValue: function(filter, args) {
    const find = function(f, args) {
      if (f.subject) {
        var result
        for (var i = 0; i < f.subject.length; i++) {
          result = find(f.subject[i], args)
          if (result !== undefined) {
            return result
          }
        }
      } else {
        if (f.left === args[0] && (!args[1] || f.link === args[1])) {
          return f.right
        } else {
          return undefined
        }
      }
    }
    var param = JSON.parse(filter)
    return find(param, args)
  },
  // 删除某个条件，args参数传值同replaceFilter
  deleteFilter: function(filter, args) {
    const deal = function(f, i) {
      f.subject.splice(i, 1)
      const re = /(and|or)/ig
      let newMask = '#0'
      let index = 1
      let seq = 1
      if (i === 0) {
        i = 1
      }
      let r
      while ((r = re.exec(f.mask))) {
        if (i !== index) {
          newMask += (r[0] + '#' + seq)
          seq++
        }
        index++
      }
      f.mask = newMask
      if (f.subject.length === 0) {
        return 'empty'
      }
      return
    }
    const del = function(f, args) {
      for (let i = 0; i < f.subject.length; i++) {
        if (f.subject[i].subject) {
          const result = del(f.subject[i], args)
          if (result === 'empty') {
            if (f.subject[i].subject !== undefined && f.subject[i].subject.length === 0) {
              return deal(f, i)
            }
          } else if (result) {
            return deal(f, i)
          }
        } else {
          if (f.subject[i].left === args[0] && (!args[1] || f.subject[i].link === args[1]) && (args[2] === undefined || f.subject[i].right === args[2])) {
            return true
          } else {
            return false
          }
        }
      }
    }
    const param = JSON.parse(filter)
    del(param, args)
    return JSON.stringify(param)
  },
  // 分析简单filter
  _parseFilter: function(filter) {
    function Reader(data) {
      this._data = data
      this._ptr = 0
      if (Reader.prototype.init === undefined) {
        Reader.prototype.eof = function() {
          return this._ptr >= this._data.length
        }
        Reader.prototype.next = function() {
          if (this.eof()) {
            return ''
          }
          return this._data.charAt(this._ptr++)
        }
        Reader.prototype.peek = function() {
          if (this.eof()) {
            return ''
          }
          return this._data.charAt(this._ptr)
        }
        Reader.prototype.init = true
      }
    }

    function Lexer(reader) {
      this._reader = reader

      if (Lexer.prototype.init === undefined) {
        Lexer.prototype.isWhitespace = function(ch) {
          return ' \t\n'.indexOf(ch) >= 0
        }
        Lexer.prototype.readWhile = function(factor) {
          let str = ''
          while (!this._reader.eof() && factor(this._reader.peek())) {
            str += this._reader.next()
          }
          return str
        }

        Lexer.prototype.readOperation = function() {
          this.readWhile(this.isWhitespace)
          if (/[!=><]/.test(this._reader.peek())) {
            return this.readWhile(function(ch) {
              return /[!=><]/.test(ch)
            })
          }

          var str = this.readNext().value
          if (str.toLowerCase() === 'not') {
            str += ' ' + this.readNext().value
          } else if (str.toLowerCase() === 'is') {
            const point = this._reader._ptr
            const str1 = this.readNext().value
            if (str1.toLowerCase() === 'not') {
              str += ' ' + str1
            } else {
              this._reader._ptr = point
            }
          }
          return str
        }

        Lexer.prototype.readToRightBracket = function() {
          let str = ''
          let n = 1
          this._reader.next()
          while (!this._reader.eof()) {
            const ch = this._reader.next()
            if (ch === '(') {
              n++
            }
            if (ch === ')') {
              n--
            }
            if (n === 0) {
              break
            }
            str += ch
          }
          return str
        }

        Lexer.prototype.readString = function(end) {
          let escaped = false
          let str = ''
          this._reader.next()
          while (!this._reader.eof()) {
            var ch = this._reader.next()
            if (escaped) {
              str += ch
              escaped = false
            } else if (ch === '\\') {
              escaped = true
            } else if (ch === end) {
              break
            } else {
              str += ch
            }
          }
          return { type: 'str', value: str }
        }

        Lexer.prototype.readNumber = function() {
          this.readWhile(this.isWhitespace)
          let has_dot = false
          const number = this.readWhile(function(ch) {
            if (ch === '.') {
              if (has_dot) return false
              has_dot = true
              return true
            }
            return /[0-9]/i.test(ch)
          })
          return { type: 'num', value: parseFloat(number) }
        }

        Lexer.prototype.readNext = function() {
          this.readWhile(this.isWhitespace)
          if (this._reader.eof()) return null
          const ch = this._reader.peek()
          if (ch === '(') {
            return { type: 'bracket', value: this.readToRightBracket() }
          }
          if (ch === '"' || ch === '\'') {
            return this.readString(ch)
          }
          if (ch >= '0' && ch <= '9') {
            return this.readNumber()
          }
          if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch === '_' || ch === '@') {
            return {
              type: 'key', value: this.readWhile(function(ch) {
                return /^[\w_.@]+$/.test(ch)
              })
            }
          }
          return { type: 'punc', value: this._reader.next() }
        }

        Lexer.prototype.parse = function() {
          const rs = []
          let thrs = null
          while (!this._reader.eof()) {
            this.readWhile(this.isWhitespace)
            if (this._reader.eof()) return rs
            var ch = this._reader.peek()
            if (thrs == null && ch === '(') {
              thrs = {}
              rs.push(thrs)
              thrs.type = 2
              thrs.cld = new Lexer(new Reader(this.readNext().value)).parse()
              thrs = {}
            } else {
              if (thrs && thrs.operation) {
                if (thrs.operation === 'between' || thrs.operation === 'not between') {
                  thrs.right = []
                  thrs.right.push(this.readNext())
                  this.readNext()
                  thrs.right.push(this.readNext())
                } else if (thrs.operation === 'in' || thrs.operation === 'not in') {
                  thrs.right = []
                  let v
                  const lx = new Lexer(new Reader(this.readNext().value))
                  while ((v = lx.readNext()) !== null) {
                    if (v.type === 'str' || v.type === 'num') {
                      thrs.right.push(v)
                    }
                  }
                } else {
                  thrs.right = this.readNext()
                }
                thrs = {}
              } else if (thrs && thrs.left) {
                thrs.operation = this.readOperation()
              } else if (thrs) {
                rs.push({ 'type': 3, 'conn': this.readNext().value })
                thrs = null
              } else {
                thrs = {}
                rs.push(thrs)
                thrs.type = 1
                thrs.left = this.readNext().value
              }
            }
          }
          return rs
        }
        Lexer.prototype.init = true
      }
    }

    const reader = new Reader(filter)
    const lexer = new Lexer(reader)
    return lexer.parse()
  },

  // 组装新格式filter
  _assembleFilter: function(parseExpr) {
    if (parseExpr.length === 0) {
      return
    }
    let expr = parseExpr[0]
    let filter
    if (expr.type === 1 || (expr.type === 2 && expr.cld.length === 1)) {
      if (expr.type === 2) {
        expr = expr.cld[0]
      }
      let right
      if (expr.right instanceof Array) {
        right = []
        for (let i = 0; i < expr.right.length; i++) {
          right.push(expr.right[i].value)
        }
      } else {
        right = expr.right.value
      }
      filter = this.getFilter(expr.left, expr.operation, right)
    } else if (expr.type === 2) {
      filter = this._assembleFilter(expr.cld)
    } else {
      return
    }

    if (parseExpr[1] && parseExpr[1].type === 3) {
      filter = this.mergeFilter([filter, this._assembleFilter(parseExpr.slice(2))], parseExpr[1].conn)
    }
    return filter
  }
}
