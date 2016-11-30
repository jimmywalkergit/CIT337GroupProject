(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyProductController', MyProductController);

    MyProductController.$inject = ['$scope', '$state', 'MyProduct'];

    function MyProductController ($scope, $state, MyProduct) {
        var vm = this;
        
        vm.myProducts = [];

        loadAll();

        function loadAll() {
            MyProduct.query(function(result) {
                vm.myProducts = result;
            });
        }
    }
})();
