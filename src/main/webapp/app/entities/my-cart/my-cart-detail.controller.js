(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyCartDetailController', MyCartDetailController);

    MyCartDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MyCart', 'MyProduct'];

    function MyCartDetailController($scope, $rootScope, $stateParams, previousState, entity, MyCart, MyProduct) {
        var vm = this;

        vm.myCart = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cit337ProjectApp:myCartUpdate', function(event, result) {
            vm.myCart = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
