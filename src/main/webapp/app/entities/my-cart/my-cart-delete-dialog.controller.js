(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyCartDeleteController',MyCartDeleteController);

    MyCartDeleteController.$inject = ['$uibModalInstance', 'entity', 'MyCart'];

    function MyCartDeleteController($uibModalInstance, entity, MyCart) {
        var vm = this;

        vm.myCart = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MyCart.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
