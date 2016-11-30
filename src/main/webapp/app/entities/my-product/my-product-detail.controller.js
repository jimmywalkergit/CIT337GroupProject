(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyProductDetailController', MyProductDetailController);

    MyProductDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MyProduct', 'MyCart', 'MyOrders'];

    function MyProductDetailController($scope, $rootScope, $stateParams, previousState, entity, MyProduct, MyCart, MyOrders) {
        var vm = this;

        vm.myProduct = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cit337ProjectApp:myProductUpdate', function(event, result) {
            vm.myProduct = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
