'use strict';

describe('Controller Tests', function() {

    describe('MyOrders Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMyOrders, MockMyCustomer, MockMyProduct;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMyOrders = jasmine.createSpy('MockMyOrders');
            MockMyCustomer = jasmine.createSpy('MockMyCustomer');
            MockMyProduct = jasmine.createSpy('MockMyProduct');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MyOrders': MockMyOrders,
                'MyCustomer': MockMyCustomer,
                'MyProduct': MockMyProduct
            };
            createController = function() {
                $injector.get('$controller')("MyOrdersDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cit337ProjectApp:myOrdersUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
