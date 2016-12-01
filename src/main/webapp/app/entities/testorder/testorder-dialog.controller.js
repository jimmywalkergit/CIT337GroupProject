(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('TestorderDialogController', TestorderDialogController);

    TestorderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Testorder', 'Testproduct'];

    function TestorderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Testorder, Testproduct) {
        var vm = this;

        vm.testorder = entity;
        vm.clear = clear;
        vm.save = save;
        vm.testproducts = Testproduct.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.testorder.id !== null) {
                Testorder.update(vm.testorder, onSaveSuccess, onSaveError);
            } else {
                Testorder.save(vm.testorder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cit337ProjectApp:testorderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
