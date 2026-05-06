import mitt from 'mitt'

type Events = {
  'send-message': string
  'update-list': number
}

const emitter = mitt<Events>()

export default emitter
