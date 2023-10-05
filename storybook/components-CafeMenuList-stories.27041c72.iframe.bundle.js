"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[960],{"./src/components/CafeMenuList.stories.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:function(){return Default},__namedExportsOrder:function(){return __namedExportsOrder},default:function(){return CafeMenuList_stories}});var mockData=__webpack_require__("./src/data/mockData.ts"),index_esm=__webpack_require__("./node_modules/react-icons/pi/index.esm.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),Resource=__webpack_require__("./src/utils/Resource.ts"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const CafeMenuList=props=>{const{menus:menus}=props,recommendedMenus=menus.filter((menuItem=>menuItem.isRecommended)),otherMenus=menus.filter((menuItem=>!menuItem.isRecommended));return(0,jsx_runtime.jsxs)(MenuList,{children:[recommendedMenus.map((menuItem=>(0,jsx_runtime.jsx)(MenuListItem,{menuItem:menuItem},menuItem.id))),otherMenus.map((menuItem=>(0,jsx_runtime.jsx)(MenuListItem,{menuItem:menuItem},menuItem.id)))]})};CafeMenuList.displayName="CafeMenuList";var components_CafeMenuList=CafeMenuList;const MenuList=styled_components_browser_esm.zo.ul`
  display: flex;
  flex-direction: column;
  gap: ${({theme:theme})=>theme.space[3]};
`,MenuListItem=props=>{const{menuItem:menuItem}=props;return(0,jsx_runtime.jsxs)(MenuListItemContainer,{children:[menuItem.imageUrl?(0,jsx_runtime.jsx)(MenuItemImage,{src:Resource.Z.getImageUrl({size:"100",filename:menuItem.imageUrl})}):(0,jsx_runtime.jsx)(MenuItemImageAlt,{children:(0,jsx_runtime.jsx)(MenuItemImageAltIcon,{})}),(0,jsx_runtime.jsx)(MenuItemName,{children:menuItem.name}),(0,jsx_runtime.jsx)(MenuItemDescription,{children:menuItem.description}),(0,jsx_runtime.jsx)(MenuItemPrice,{children:menuItem.price})]})};MenuListItem.displayName="MenuListItem";const MenuListItemContainer=styled_components_browser_esm.zo.li`
  display: grid;
  grid-auto-rows: auto;
  grid-template-columns: 120px 1fr;
  grid-template-rows: auto 1fr;
  row-gap: ${({theme:theme})=>theme.space[1]};
  column-gap: ${({theme:theme})=>theme.space[2]};

  height: 120px;
  padding: ${({theme:theme})=>theme.space[2]};

  & > :first-child {
    grid-row: 1 / span 3;
    aspect-ratio: 1 / 1;
    height: 100%;
    border-radius: 8px;
  }
`,MenuItemImage=styled_components_browser_esm.zo.img``,MenuItemImageAlt=styled_components_browser_esm.zo.div`
  display: flex;
  align-items: center;
  justify-content: center;

  background: #dedede;
  border-radius: 50%;
`,MenuItemImageAltIcon=(0,styled_components_browser_esm.zo)(index_esm.yAJ)`
  width: 50%;
  height: 50%;
`,MenuItemName=styled_components_browser_esm.zo.h3`
  margin-top: ${({theme:theme})=>theme.space[3]};
  font-size: ${({theme:theme})=>theme.fontSize.lg};
`,MenuItemDescription=styled_components_browser_esm.zo.p`
  font-size: ${({theme:theme})=>theme.fontSize.sm};
  color: ${({theme:theme})=>theme.color.gray};
`,MenuItemPrice=styled_components_browser_esm.zo.p`
  font-size: ${({theme:theme})=>theme.fontSize.sm};
  color: ${({theme:theme})=>theme.color.gray};
`;try{CafeMenuList.displayName="CafeMenuList",CafeMenuList.__docgenInfo={description:"",displayName:"CafeMenuList",props:{menus:{defaultValue:null,description:"",name:"menus",required:!0,type:{name:"CafeMenuItem[]"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/CafeMenuList.tsx#CafeMenuList"]={docgenInfo:CafeMenuList.__docgenInfo,name:"CafeMenuList",path:"src/components/CafeMenuList.tsx#CafeMenuList"})}catch(__react_docgen_typescript_loader_error){}var CafeMenuList_stories={title:"CafeMenuList",component:components_CafeMenuList};const Default={args:{menus:[...mockData.CO[0].menus,...mockData.CO[0].menus,...mockData.CO[0].menus]}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    menus: [...cafeMenus[0].menus, ...cafeMenus[0].menus, ...cafeMenus[0].menus]\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]},"./src/utils/Resource.ts":function(__unused_webpack_module,__webpack_exports__){class Resource{constructor(){}static getImageUrl(options){const{filename:filename,size:size}=options;return Resource.joinPath("/images",size,filename)}static getAssetUrl(options){const{filename:filename}=options;return Resource.joinPath("/assets",filename)}static joinPath(...paths){return paths.map(((part,index)=>(index>0&&(part=part.replace(/^\/+/,"")),index!==paths.length-1&&(part=part.replace(/\/+$/,"")),part))).join("/")}}__webpack_exports__.Z=Resource}}]);
//# sourceMappingURL=components-CafeMenuList-stories.27041c72.iframe.bundle.js.map