import { createRouter, createWebHistory } from 'vue-router'
import Props from '@/pages/01_props/PropsFather.vue'
import Event from '@/pages/02_custom-event/CustomEventFather.vue'
import Bus from '@/pages/03_mitt/MittFather.vue'
import Model from '@/pages/04_v-model/v-modelFather.vue'
import AttrsListeners from '@/pages/05_attrs/AttrsFather.vue'
import RefChildrenParent from '@/pages/06_refs-parent/RefsParentFather.vue'
import ProvideInject from '@/pages/07_provide-inject/ProvideInjectFather.vue'
import Pinia from '@/pages/08_pinia/PiniaFather.vue'
import Slot from '@/pages/09_slot/SlotFather.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/props',
      name: 'props',
      component: Props,
    },
    {
      path: '/event',
      name: 'event',
      component: Event,
    },
    {
      path: '/bus',
      name: 'bus',
      component: Bus,
    },
    {
      path: '/model',
      name: 'model',
      component: Model,
    },
    {
      path: '/attrs-listeners',
      name: 'attrs-listeners',
      component: AttrsListeners,
    },
    {
      path: '/refs-children-parent',
      name: 'refs-children-parent',
      component: RefChildrenParent,
    },
    {
      path: '/provide-inject',
      name: 'provide-inject',
      component: ProvideInject,
    },
    {
      path: '/pinia',
      name: 'pinia',
      component: Pinia,
    },
    {
      path: '/slot',
      name: 'slot',
      component: Slot,
    },
  ],
})

export default router
