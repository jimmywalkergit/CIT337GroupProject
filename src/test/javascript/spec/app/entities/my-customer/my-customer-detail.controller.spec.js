'use strict';

describe('Controller Tests', function() {

    describe('MyCustomer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMyCustomer, MockMyCart, MockMyOrders;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMyCustomer = jasmine.createSpy('MockMyCustomer');
            MockMyCart = jasmine.createSpy('MockMyCart');
            MockMyOrders = jasmine.createSpy('MockMyOrders');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MyCustomer': MockMyCustomer,
                'MyCart': MockMyCart,
                'MyOrders': MockMyOrders
            };
            createController = function() {
                $injector.get('$controller')("MyCustomerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cit337ProjectApp:myCustomerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
