(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrderproductDetailController', OrderproductDetailController);

    OrderproductDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Orderproduct'];

    function OrderproductDetailController($scope, $rootScope, $stateParams, previousState, entity, Orderproduct) {
        var vm = this;

        vm.orderproduct = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cit337ProjectApp:orderproductUpdate', function(event, result) {
            vm.orderproduct = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
