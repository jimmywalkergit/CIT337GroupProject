(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyOrdersDetailController', MyOrdersDetailController);

    MyOrdersDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MyOrders', 'MyCustomer', 'MyProduct'];

    function MyOrdersDetailController($scope, $rootScope, $stateParams, previousState, entity, MyOrders, MyCustomer, MyProduct) {
        var vm = this;

        vm.myOrders = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cit337ProjectApp:myOrdersUpdate', function(event, result) {
            vm.myOrders = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
