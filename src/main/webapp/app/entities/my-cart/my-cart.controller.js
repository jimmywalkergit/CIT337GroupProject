(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyCartController', MyCartController);

    MyCartController.$inject = ['$scope', '$state', 'MyCart','Testproduct','MyProduct', 'Testorder'];

    function MyCartController ($scope, $state, MyCart, Testproduct, MyProduct, Testorder) {
        var vm = this;
        vm.myCarts = [];
        vm.testproducts = Testproduct.query();
        vm.myproducts = MyProduct.query();
        vm.testorders = Testorder.query();

        loadAll();

        function loadAll() {
            MyCart.query(function(result) {
                vm.myCarts = result;
            });
        }
    }
    function getTotal(){



    }

})();
