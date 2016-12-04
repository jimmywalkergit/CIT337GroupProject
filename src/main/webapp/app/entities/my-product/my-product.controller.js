(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyProductController', MyProductController);

    MyProductController.$inject = ['$scope', '$state', 'MyProduct','Testproduct','Orderproduct'];

    function MyProductController ($scope, $state, MyProduct, Testproduct, Orderproduct) {
        var vm = this;
                vm.testproducts = Orderproduct.query();

                vm.myProducts = [];


                 loadAll();

                 function loadAll() {
                     MyProduct.query(function(result) {
                         vm.myProducts = result;
                     });
                 }
    }
})();
