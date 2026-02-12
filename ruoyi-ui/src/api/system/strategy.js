import request from '@/utils/request'

// 查询学习攻略列表
export function listStrategy(query) {
  return request({
    url: '/business/strategy/list',
    method: 'get',
    params: query
  })
}

// 查询学习攻略详细
export function getStrategy(strategyId) {
  return request({
    url: '/business/strategy/' + strategyId,
    method: 'get'
  })
}

// 新增学习攻略
export function addStrategy(data) {
  return request({
    url: '/business/strategy',
    method: 'post',
    data: data
  })
}

// 修改学习攻略
export function updateStrategy(data) {
  return request({
    url: '/business/strategy',
    method: 'put',
    data: data
  })
}

// 删除学习攻略
export function delStrategy(strategyId) {
  return request({
    url: '/business/strategy/' + strategyId,
    method: 'delete'
  })
}