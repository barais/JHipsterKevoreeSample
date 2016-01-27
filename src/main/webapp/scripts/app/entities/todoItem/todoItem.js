'use strict';

angular.module('diversescholApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('todoItem', {
                parent: 'entity',
                url: '/todoItems',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'diversescholApp.todoItem.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/todoItem/todoItems.html',
                        controller: 'TodoItemController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('todoItem');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('todoItem.detail', {
                parent: 'entity',
                url: '/todoItem/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'diversescholApp.todoItem.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/todoItem/todoItem-detail.html',
                        controller: 'TodoItemDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('todoItem');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TodoItem', function($stateParams, TodoItem) {
                        return TodoItem.get({id : $stateParams.id});
                    }]
                }
            })
            .state('todoItem.new', {
                parent: 'todoItem',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/todoItem/todoItem-dialog.html',
                        controller: 'TodoItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    content: null,
                                    endDate: null,
                                    done: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('todoItem', null, { reload: true });
                    }, function() {
                        $state.go('todoItem');
                    })
                }]
            })
            .state('todoItem.edit', {
                parent: 'todoItem',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/todoItem/todoItem-dialog.html',
                        controller: 'TodoItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TodoItem', function(TodoItem) {
                                return TodoItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('todoItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('todoItem.delete', {
                parent: 'todoItem',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/todoItem/todoItem-delete-dialog.html',
                        controller: 'TodoItemDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TodoItem', function(TodoItem) {
                                return TodoItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('todoItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
