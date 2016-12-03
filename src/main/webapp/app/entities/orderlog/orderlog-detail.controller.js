(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrderlogDetailController', OrderlogDetailController);

    OrderlogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Orderlog', 'Orderproduct', 'Ordercustomer'];

    function OrderlogDetailController($scope, $rootScope, $stateParams, previousState, entity, Orderlog, Orderproduct, Ordercustomer) {
        var vm = this;

        vm.orderlog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cit337ProjectApp:orderlogUpdate', function(event, result) {
            vm.orderlog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
