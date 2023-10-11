"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[223],{"./src/components/ImageModal.stories.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:function(){return Default},__namedExportsOrder:function(){return __namedExportsOrder},default:function(){return ImageModal_stories}});var mockData=__webpack_require__("./src/data/mockData.ts"),react=__webpack_require__("./node_modules/react/index.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),Resource=__webpack_require__("./src/utils/Resource.ts"),useScrollSnapGuard=__webpack_require__("./src/components/ScrollSnap/hooks/useScrollSnapGuard.ts"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const ImageModal=props=>{const{imageUrls:imageUrls,onClose:onClose}=props,[activeImage,setActiveImage]=(0,react.useState)(imageUrls[0]),scrollSnapGuardHandlers=(0,useScrollSnapGuard.Z)();return(0,jsx_runtime.jsxs)(Container,{...scrollSnapGuardHandlers,children:[(0,jsx_runtime.jsx)(ActiveImageContainer,{onClick:event=>{event.currentTarget===event.target&&onClose()},children:(0,jsx_runtime.jsx)(ActiveImage,{src:Resource.Z.getImageUrl({size:"original",filename:activeImage})})}),(0,jsx_runtime.jsx)(ImageList,{children:imageUrls.map(((imageUrl,index)=>(0,jsx_runtime.jsx)(ImageListItem,{children:(0,jsx_runtime.jsx)(ImageListItemButton,{onClick:()=>setActiveImage(imageUrl),children:(0,jsx_runtime.jsx)(ImageListItemImage,{src:Resource.Z.getImageUrl({size:"original",filename:imageUrl})})})},index)))})]})};ImageModal.displayName="ImageModal";var components_ImageModal=ImageModal;const Container=styled_components_browser_esm.zo.div`
  position: absolute;
  z-index: 1000;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;

  display: flex;
  flex-direction: column;

  background: rgba(0, 0, 0, 0.5);
`,ActiveImageContainer=styled_components_browser_esm.zo.div`
  touch-action: pan-x pan-y;

  display: flex;
  flex: 1;
  align-items: center;
  justify-content: center;
`,ActiveImage=styled_components_browser_esm.zo.img`
  width: 100%;
`,ImageList=styled_components_browser_esm.zo.ul`
  overflow-x: auto;
  display: flex;
  height: 80px;
  margin-top: auto;
`,ImageListItem=styled_components_browser_esm.zo.li`
  flex-shrink: 0;
  width: 80px;
`,ImageListItemButton=styled_components_browser_esm.zo.button`
  width: 100%;
  height: 100%;
`,ImageListItemImage=styled_components_browser_esm.zo.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;try{ImageModal.displayName="ImageModal",ImageModal.__docgenInfo={description:"",displayName:"ImageModal",props:{imageUrls:{defaultValue:null,description:"",name:"imageUrls",required:!0,type:{name:"string[]"}},onClose:{defaultValue:null,description:"",name:"onClose",required:!0,type:{name:"() => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/ImageModal.tsx#ImageModal"]={docgenInfo:ImageModal.__docgenInfo,name:"ImageModal",path:"src/components/ImageModal.tsx#ImageModal"})}catch(__react_docgen_typescript_loader_error){}var ImageModal_stories={title:"ImageModal",component:components_ImageModal};const Default={args:{imageUrls:mockData.CO[0].menuBoards.map((menuBoard=>menuBoard.imageUrl))}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    imageUrls: cafeMenus[0].menuBoards.map(menuBoard => menuBoard.imageUrl)\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]},"./src/components/ScrollSnap/hooks/useScrollSnapGuard.ts":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var react__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/index.js");__webpack_exports__.Z=()=>{const preventPropagation=(0,react__WEBPACK_IMPORTED_MODULE_0__.useCallback)((event=>{event.stopPropagation()}),[]);return(0,react__WEBPACK_IMPORTED_MODULE_0__.useMemo)((()=>({onTouchStart:preventPropagation,onTouchMove:preventPropagation,onTouchEnd:preventPropagation,onMouseDown:preventPropagation,onMouseMove:preventPropagation,onMouseUp:preventPropagation,onMouseLeave:preventPropagation})),[preventPropagation])}},"./src/utils/Resource.ts":function(__unused_webpack_module,__webpack_exports__){class Resource{constructor(){}static getImageUrl(options){const{filename:filename,size:size}=options;return Resource.joinPath("/images",size,filename)}static getAssetUrl(options){const{filename:filename}=options;return Resource.joinPath("/assets",filename)}static joinPath(...paths){return paths.map(((part,index)=>(index>0&&(part=part.replace(/^\/+/,"")),index!==paths.length-1&&(part=part.replace(/\/+$/,"")),part))).join("/")}}__webpack_exports__.Z=Resource}}]);
//# sourceMappingURL=components-ImageModal-stories.8a5779d4.iframe.bundle.js.map