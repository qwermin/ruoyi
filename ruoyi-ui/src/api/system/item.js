import request from '@/utils/request'

// 查询物品列表
export function listItem(query) {
  return request({
    url: '/business/item/list',
    method: 'get',
    params: query
  })
}

// 查询物品详细
export function getItem(itemId) {
  return request({
    url: '/business/item/' + itemId,
    method: 'get'
  })
}

// 新增物品
export function addItem(data) {
  return request({
    url: '/business/item',
    method: 'post',
    data: data
  })
}

// 修改物品
export function updateItem(data) {
  return request({
    url: '/business/item',
    method: 'put',
    data: data
  })
}

// 删除物品
export function delItem(itemId) {
  return request({
    url: '/business/item/' + itemId,
    method: 'delete'
  })
}