// 引入 mitt
import mitt from 'mitt'

// 调用 mitt 得到 mitter, mitter 可以绑定事件和触发事件
// 创建一个事件总线实例：emitter
const emitter = mitt()

// 绑定事件：emitter.on('事件名称', 回调函数)
emitter.on('test-event', (data) => {
  console.log('接收到 test-event 事件，数据为：', data)
})

// 触发事件：emitter.emit('事件名称', 数据)
setInterval(() => {
  emitter.emit('test-event', { time: new Date().toLocaleTimeString() })
}, 1000)

// 解绑事件：emitter.off('事件名称', 回调函数)
setTimeout(() => {
  emitter.off('test-event')
  console.log('已解绑 test-event 事件')
  // 全部解绑可以使用 emitter.all。clear() 方法
}, 5000)

// 在其他文件中使用 emitter 时，可以直接导入该实例，然后使用 on 和 emit 方法进行事件的绑定和触发。

// 导出 emitter
export default emitter
