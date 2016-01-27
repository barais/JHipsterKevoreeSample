 'use strict';

angular.module('diversescholApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-diversescholApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-diversescholApp-params')});
                }
                return response;
            }
        };
    });
