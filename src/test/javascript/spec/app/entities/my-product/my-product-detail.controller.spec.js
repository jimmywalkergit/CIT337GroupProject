'use strict';

describe('Controller Tests', function() {

    describe('MyProduct Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMyProduct, MockMyCart, MockMyOrders;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMyProduct = jasmine.createSpy('MockMyProduct');
            MockMyCart = jasmine.createSpy('MockMyCart');
            MockMyOrders = jasmine.createSpy('MockMyOrders');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MyProduct': MockMyProduct,
                'MyCart': MockMyCart,
                'MyOrders': MockMyOrders
            };
            createController = function() {
                $injector.get('$controller')("MyProductDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cit337ProjectApp:myProductUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
