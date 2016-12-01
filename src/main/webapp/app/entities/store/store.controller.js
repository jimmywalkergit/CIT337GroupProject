(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('StoreController', StoreController);

    StoreController.$inject = ['$scope', '$state', 'Store'];

    function StoreController ($scope, $state, Store) {
        var vm = this;


        vm.stores = [];

        loadAll();

        function loadAll() {
            Store.query(function(result) {
                vm.stores = result;
            });
        }
    }
})();




