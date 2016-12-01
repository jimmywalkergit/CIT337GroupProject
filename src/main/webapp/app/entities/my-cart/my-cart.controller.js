(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyCartController', MyCartController);

    MyCartController.$inject = ['$scope', '$state', 'MyCart','Testproduct'];

    function MyCartController ($scope, $state, MyCart) {
        var vm = this;
        vm.myCarts = [];
        vm.testproducts = Testproduct.query();


        loadAll();

        function loadAll() {
            MyCart.query(function(result) {
                vm.myCarts = result;
            });
        }
    }
})();
