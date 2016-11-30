(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrdersDetailController', OrdersDetailController);

    OrdersDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Orders', 'Customer', 'Product', 'Employee'];

    function OrdersDetailController($scope, $rootScope, $stateParams, previousState, entity, Orders, Customer, Product, Employee) {
        var vm = this;

        vm.orders = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cit337ProjectApp:ordersUpdate', function(event, result) {
            vm.orders = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
