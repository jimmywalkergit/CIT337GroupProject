(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrderlogDeleteController',OrderlogDeleteController);

    OrderlogDeleteController.$inject = ['$uibModalInstance', 'entity', 'Orderlog'];

    function OrderlogDeleteController($uibModalInstance, entity, Orderlog) {
        var vm = this;

        vm.orderlog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Orderlog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
