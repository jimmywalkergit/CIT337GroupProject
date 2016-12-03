(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrderproductController', OrderproductController);

    OrderproductController.$inject = ['$scope', '$state', 'Orderproduct'];

    function OrderproductController ($scope, $state, Orderproduct) {
        var vm = this;
        
        vm.orderproducts = [];

        loadAll();

        function loadAll() {
            Orderproduct.query(function(result) {
                vm.orderproducts = result;
            });
        }
    }
})();
