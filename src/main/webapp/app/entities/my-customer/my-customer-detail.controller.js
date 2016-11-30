(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyCustomerDetailController', MyCustomerDetailController);

    MyCustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MyCustomer', 'MyCart', 'MyOrders'];

    function MyCustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, MyCustomer, MyCart, MyOrders) {
        var vm = this;

        vm.myCustomer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cit337ProjectApp:myCustomerUpdate', function(event, result) {
            vm.myCustomer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
