import request from '@/utils/request'

export function listPrice(query) {
  return request({
    url: '/business/price/list',
    method: 'get',
    params: query
  })
}

export function getPrice(priceId) {
  return request({
    url: '/business/price/' + priceId,
    method: 'get'
  })
}

export function addPrice(data) {
  return request({
    url: '/business/price',
    method: 'post',
    data: data
  })
}

export function updatePrice(data) {
  return request({
    url: '/business/price',
    method: 'put',
    data: data
  })
}

export function delPrice(priceId) {
  return request({
    url: '/business/price/' + priceId,
    method: 'delete'
  })
}

export function exportPrice(query) {
  return request({
    url: '/business/price/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
