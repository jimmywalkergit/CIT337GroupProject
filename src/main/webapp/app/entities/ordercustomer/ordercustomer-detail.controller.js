(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrdercustomerDetailController', OrdercustomerDetailController);

    OrdercustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ordercustomer'];

    function OrdercustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, Ordercustomer) {
        var vm = this;

        vm.ordercustomer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cit337ProjectApp:ordercustomerUpdate', function(event, result) {
            vm.ordercustomer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
