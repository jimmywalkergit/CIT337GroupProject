(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('CartDetailController', CartDetailController);

    CartDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cart', 'Product'];

    function CartDetailController($scope, $rootScope, $stateParams, previousState, entity, Cart, Product) {
        var vm = this;

        vm.cart = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cit337ProjectApp:cartUpdate', function(event, result) {
            vm.cart = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
