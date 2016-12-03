'use strict';

describe('Controller Tests', function() {

    describe('Orderlog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOrderlog, MockOrderproduct, MockOrdercustomer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOrderlog = jasmine.createSpy('MockOrderlog');
            MockOrderproduct = jasmine.createSpy('MockOrderproduct');
            MockOrdercustomer = jasmine.createSpy('MockOrdercustomer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Orderlog': MockOrderlog,
                'Orderproduct': MockOrderproduct,
                'Ordercustomer': MockOrdercustomer
            };
            createController = function() {
                $injector.get('$controller')("OrderlogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cit337ProjectApp:orderlogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
