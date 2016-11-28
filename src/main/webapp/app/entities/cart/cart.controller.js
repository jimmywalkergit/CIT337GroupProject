(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('CartController', CartController);

    CartController.$inject = ['$scope', '$state', 'Cart'];

    function CartController ($scope, $state, Cart) {
        var vm = this;
        
        vm.carts = [];

        loadAll();

        function loadAll() {
            Cart.query(function(result) {
                vm.carts = result;
            });
        }
    }
})();
