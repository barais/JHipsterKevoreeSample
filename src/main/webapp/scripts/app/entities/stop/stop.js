'use strict';

angular.module('diversescholApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stop', {
                parent: 'entity',
                url: '/stop',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'diversescholApp.todoItem.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stop/stop.html',
                        controller: 'StopController'
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
    });
