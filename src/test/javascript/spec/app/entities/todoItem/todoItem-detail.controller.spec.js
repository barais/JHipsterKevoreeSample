'use strict';

describe('Controller Tests', function() {

    describe('TodoItem Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTodoItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTodoItem = jasmine.createSpy('MockTodoItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TodoItem': MockTodoItem
            };
            createController = function() {
                $injector.get('$controller')("TodoItemDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'diversescholApp:todoItemUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
