(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyOrdersDeleteController',MyOrdersDeleteController);

    MyOrdersDeleteController.$inject = ['$uibModalInstance', 'entity', 'MyOrders'];

    function MyOrdersDeleteController($uibModalInstance, entity, MyOrders) {
        var vm = this;

        vm.myOrders = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MyOrders.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
