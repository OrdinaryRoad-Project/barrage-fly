/*
 * Copyright 2023 OrdinaryRoad
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { urlEncode } from 'ordinaryroad-vuetify/src/utils'

let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  apis: {
    create: (data, start = false) => {
      return $axios({ url: `/task?start=${start}`, method: 'post', data })
    },
    delete: (id) => {
      return $axios({ url: `/task?id=${id}`, method: 'delete' })
    },
    update: (data, start = false) => {
      const _data = Object.assign({}, data)
      _data.id = null
      _data.uuid = null
      return $axios({ url: `/task?id=${data.id}&start=${start}`, method: 'put', data: _data })
    },
    validate: (data) => {
      const _data = Object.assign({}, data)
      _data.id = null
      _data.uuid = null
      return $axios({ url: '/task/validate', method: 'put', data: _data })
    },
    validateExpress: ({ msgPreMapExpress, msgFilterExpress, msgPostMapExpress }) => {
      const data = { msgPreMapExpress, msgFilterExpress, msgPostMapExpress }
      return $axios({ url: '/task/validate/express', method: 'put', data })
    },
    updateCookie: ({ id, cookie }) => {
      const data = cookie
      return $axios({
        url: `/task/cookie?id=${id}`,
        method: 'put',
        headers: { 'content-type': 'text/plain' },
        data
      })
    },
    findById: (id = '') => {
      return $axios({ url: `/task?id=${id}`, method: 'get' })
    },
    getStatus: (id = '') => {
      return $axios({ url: `/task/status/?id=${id}`, method: 'get' })
    },
    page: (offset, limit, sortBy = [], sortDesc = [], searchParams = { platform: '', roomId: '', remark: '' }) => {
      return $axios({
        url: `/task/page/${offset}/${limit}/?${urlEncode(searchParams)}&${urlEncode(sortBy, 'sortBy')}${urlEncode(sortDesc, 'sortDesc')}`,
        method: 'get'
      })
    },
    start: (ids = []) => {
      return $axios({ url: `/task/start?${urlEncode(ids, 'ids')}`, method: 'get' })
    },
    stop: (ids = []) => {
      return $axios({ url: `/task/stop?${urlEncode(ids, 'ids')}`, method: 'get' })
    },
    statuses () {
      return $axios({ url: '/task/statuses', method: 'get' })
    },
    platformOptions () {
      return $axios({ url: '/task/platform/options', method: 'get' })
    },
    platformConfigs () {
      return $axios({ url: '/task/platform/configs', method: 'get' })
    }
  }
}
