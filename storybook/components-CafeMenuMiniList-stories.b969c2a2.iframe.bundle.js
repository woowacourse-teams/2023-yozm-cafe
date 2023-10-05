"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[228],{"./src/components/CafeMenuMiniList.stories.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:function(){return Default},__namedExportsOrder:function(){return __namedExportsOrder}});var _data_mockData__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/data/mockData.ts");const meta={title:"CafeMenuMiniList",component:__webpack_require__("./src/components/CafeMenuMiniList.tsx").Z};__webpack_exports__.default=meta;const Default={args:{menus:[..._data_mockData__WEBPACK_IMPORTED_MODULE_0__.CO[0].menus,..._data_mockData__WEBPACK_IMPORTED_MODULE_0__.CO[0].menus,..._data_mockData__WEBPACK_IMPORTED_MODULE_0__.CO[0].menus]}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    menus: [...cafeMenus[0].menus, ...cafeMenus[0].menus, ...cafeMenus[0].menus]\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]},"./src/components/CafeMenuMiniList.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var react_icons_pi__WEBPACK_IMPORTED_MODULE_3__=__webpack_require__("./node_modules/react-icons/pi/index.esm.js"),styled_components__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_utils_Resource__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./src/utils/Resource.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/jsx-runtime.js");const CafeMenuMiniList=props=>{const{menus:menus}=props;return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuList,{children:menus.map((menuItem=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuListItem,{menuItem:menuItem},menuItem.id)))})};CafeMenuMiniList.displayName="CafeMenuMiniList",__webpack_exports__.Z=CafeMenuMiniList;const MenuList=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.ul`
  overflow-y: auto;
  display: flex;
  flex-shrink: 0;
  gap: ${({theme:theme})=>theme.space[4]};
`,MenuListItem=props=>{const{menuItem:menuItem}=props;return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxs)(MenuListItemContainer,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemImageContainer,{children:menuItem.imageUrl?(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemImage,{src:_utils_Resource__WEBPACK_IMPORTED_MODULE_2__.Z.getImageUrl({size:"100",filename:menuItem.imageUrl})}):(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemImageAlt,{children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemImageAltIcon,{})})}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemName,{children:menuItem.name})]})};MenuListItem.displayName="MenuListItem";const MenuListItemContainer=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.li`
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  align-items: center;

  width: 70px;
  height: 100%;
`,MenuItemImageContainer=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.div`
  width: 100%;
  height: 100%;
  padding: ${({theme:theme})=>theme.space[2]};

  & > * {
    aspect-ratio: 1 / 1;
    width: 100%;
  }
`,MenuItemImage=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.img`
  display: block;
  object-fit: cover;
  border-radius: 50%;
`,MenuItemImageAlt=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.div`
  display: flex;
  align-items: center;
  justify-content: center;

  background: #dedede;
  border-radius: 50%;
`,MenuItemImageAltIcon=(0,styled_components__WEBPACK_IMPORTED_MODULE_1__.zo)(react_icons_pi__WEBPACK_IMPORTED_MODULE_3__.yAJ)`
  width: 50%;
  height: 50%;
`,MenuItemName=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.div`
  font-size: ${({theme:theme})=>theme.fontSize.xs};
`;styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.hr`
  margin: ${({theme:theme})=>theme.space[2]} ${({theme:theme})=>theme.space[1]};
  border: none;
  border-left: 1px solid ${({theme:theme})=>theme.color.gray};
`;try{CafeMenuMiniList.displayName="CafeMenuMiniList",CafeMenuMiniList.__docgenInfo={description:"",displayName:"CafeMenuMiniList",props:{menus:{defaultValue:null,description:"",name:"menus",required:!0,type:{name:"CafeMenuItem[]"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/CafeMenuMiniList.tsx#CafeMenuMiniList"]={docgenInfo:CafeMenuMiniList.__docgenInfo,name:"CafeMenuMiniList",path:"src/components/CafeMenuMiniList.tsx#CafeMenuMiniList"})}catch(__react_docgen_typescript_loader_error){}},"./src/utils/Resource.ts":function(__unused_webpack_module,__webpack_exports__){class Resource{constructor(){}static getImageUrl(options){const{filename:filename,size:size}=options;return Resource.joinPath("/images",size,filename)}static getAssetUrl(options){const{filename:filename}=options;return Resource.joinPath("/assets",filename)}static joinPath(...paths){return paths.map(((part,index)=>(index>0&&(part=part.replace(/^\/+/,"")),index!==paths.length-1&&(part=part.replace(/\/+$/,"")),part))).join("/")}}__webpack_exports__.Z=Resource}}]);
//# sourceMappingURL=components-CafeMenuMiniList-stories.b969c2a2.iframe.bundle.js.map