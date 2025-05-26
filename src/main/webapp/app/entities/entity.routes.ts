import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'Authorities' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'societe',
    data: { pageTitle: 'Societes' },
    loadChildren: () => import('./societe/societe.routes'),
  },
  {
    path: 'etablissement',
    data: { pageTitle: 'Etablissements' },
    loadChildren: () => import('./etablissement/etablissement.routes'),
  },
  {
    path: 'point-de-vente',
    data: { pageTitle: 'PointDeVentes' },
    loadChildren: () => import('./point-de-vente/point-de-vente.routes'),
  },
  {
    path: 'hier',
    data: { pageTitle: 'Hiers' },
    loadChildren: () => import('./hier/hier.routes'),
  },
  {
    path: 'employe',
    data: { pageTitle: 'Employes' },
    loadChildren: () => import('./employe/employe.routes'),
  },
  {
    path: 'menu',
    data: { pageTitle: 'Menus' },
    loadChildren: () => import('./menu/menu.routes'),
  },
  {
    path: 'element-menu',
    data: { pageTitle: 'ElementMenus' },
    loadChildren: () => import('./element-menu/element-menu.routes'),
  },
  {
    path: 'family-group',
    data: { pageTitle: 'FamilyGroups' },
    loadChildren: () => import('./family-group/family-group.routes'),
  },
  {
    path: 'major-group',
    data: { pageTitle: 'MajorGroups' },
    loadChildren: () => import('./major-group/major-group.routes'),
  },
  {
    path: 'code-raison',
    data: { pageTitle: 'CodeRaisons' },
    loadChildren: () => import('./code-raison/code-raison.routes'),
  },
  {
    path: 'remise',
    data: { pageTitle: 'Remises' },
    loadChildren: () => import('./remise/remise.routes'),
  },
  {
    path: 'commission-service',
    data: { pageTitle: 'CommissionServices' },
    loadChildren: () => import('./commission-service/commission-service.routes'),
  },
  {
    path: 'mode-paiement',
    data: { pageTitle: 'ModePaiements' },
    loadChildren: () => import('./mode-paiement/mode-paiement.routes'),
  },
  {
    path: 'taxe',
    data: { pageTitle: 'Taxes' },
    loadChildren: () => import('./taxe/taxe.routes'),
  },
  {
    path: 'barcode',
    data: { pageTitle: 'Barcodes' },
    loadChildren: () => import('./barcode/barcode.routes'),
  },
  {
    path: 'categorie-point-de-vente',
    data: { pageTitle: 'CategoriePointDeVentes' },
    loadChildren: () => import('./categorie-point-de-vente/categorie-point-de-vente.routes'),
  },
  {
    path: 'facture-en-tete',
    data: { pageTitle: 'FactureEnTetes' },
    loadChildren: () => import('./facture-en-tete/facture-en-tete.routes'),
  },
  {
    path: 'facture-detail',
    data: { pageTitle: 'FactureDetails' },
    loadChildren: () => import('./facture-detail/facture-detail.routes'),
  },
  {
    path: 'partie-de-journee',
    data: { pageTitle: 'PartieDeJournees' },
    loadChildren: () => import('./partie-de-journee/partie-de-journee.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
