(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrderlogController', OrderlogController);

    OrderlogController.$inject = ['$scope', '$state', 'Orderlog'];

    function OrderlogController ($scope, $state, Orderlog) {
        var vm = this;
        
        vm.orderlogs = [];

        loadAll();

        function loadAll() {
            Orderlog.query(function(result) {
                vm.orderlogs = result;
            });
        }
    }
})();
