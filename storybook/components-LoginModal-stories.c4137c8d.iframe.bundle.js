"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[639],{"./src/components/LoginModal.stories.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:function(){return Default},__namedExportsOrder:function(){return __namedExportsOrder},default:function(){return LoginModal_stories}});var chunk_AY7I2SME=__webpack_require__("./node_modules/@storybook/addon-actions/dist/chunk-AY7I2SME.mjs");var LoginModal_stories={title:"LoginModal",component:__webpack_require__("./src/components/LoginModal.tsx").Z};const Default={args:{onClose:(0,chunk_AY7I2SME.aD)("close")}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    onClose: action('close')\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]},"./src/components/LoginButton.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_styles_theme__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/styles/theme.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const LoginButton=props=>{const{children:children,...rest}=props;return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(Container,{...rest,children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(ButtonContent,{children:children})})};LoginButton.displayName="LoginButton",__webpack_exports__.Z=LoginButton;const Container=styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.button`
  width: 44px;
  height: 44px;

  font-size: ${({theme:theme})=>theme.fontSize.base};
  font-weight: 500;

  background-color: ${props=>_styles_theme__WEBPACK_IMPORTED_MODULE_0__.Z.color[props.$color]};
  border: none;
  border-radius: 4px;
  box-shadow: ${({theme:theme})=>theme.shadow[1]};
`,ButtonContent=styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.div`
  display: flex;
  justify-content: center;
`;try{LoginButton.displayName="LoginButton",LoginButton.__docgenInfo={description:"",displayName:"LoginButton",props:{$color:{defaultValue:null,description:"",name:"$color",required:!0,type:{name:"enum",value:[{value:'"text"'},{value:'"primary"'},{value:'"secondary"'},{value:'"tertiary"'},{value:'"yellow"'},{value:'"gray"'},{value:'"black"'},{value:'"white"'},{value:'"error"'},{value:'"warning"'},{value:'"success"'},{value:'"line"'},{value:'"background"'}]}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/LoginButton.tsx#LoginButton"]={docgenInfo:LoginButton.__docgenInfo,name:"LoginButton",path:"src/components/LoginButton.tsx#LoginButton"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/LoginModal.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.d(__webpack_exports__,{Z:function(){return components_LoginModal}});var index_esm=__webpack_require__("./node_modules/react-icons/cg/index.esm.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),client=__webpack_require__("./src/client.ts"),useSuspenseQuery=__webpack_require__("./src/hooks/useSuspenseQuery.ts");var hooks_useAuthUrls=()=>(0,useSuspenseQuery.Z)({queryKey:["auth","urls"],queryFn:()=>client.Z.getAuthUrls()}),Resource=__webpack_require__("./src/utils/Resource.ts"),LoginButton=__webpack_require__("./src/components/LoginButton.tsx"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const brandColors={kakao:"yellow",google:"white"},LoginModal=props=>{const{onClose:onClose}=props,{data:urls}=hooks_useAuthUrls();return(0,jsx_runtime.jsx)(ModalContainer,{onClick:onClose,children:(0,jsx_runtime.jsxs)(ModalContent,{onClick:event=>{event.stopPropagation()},children:[(0,jsx_runtime.jsx)(CloseButtonContainer,{"aria-label":"닫기 버튼",role:"dialog","aria-modal":"true","aria-hidden":"true",children:(0,jsx_runtime.jsx)(CloseIcon,{onClick:onClose})}),(0,jsx_runtime.jsx)(Logo,{}),(0,jsx_runtime.jsx)(LoginTitle,{children:"간편 로그인"}),(0,jsx_runtime.jsx)(ButtonContainer,{children:urls.map((({provider:provider,authorizationUrl:authorizationUrl})=>(0,jsx_runtime.jsx)("a",{href:authorizationUrl,children:(0,jsx_runtime.jsx)(LoginButton.Z,{$color:brandColors[provider]??"white","aria-label":`${provider} 로그인`,children:(0,jsx_runtime.jsx)("img",{src:Resource.Z.getAssetUrl({filename:`${provider}.svg`}),alt:`${provider} 로고`})})},provider)))})]})})};LoginModal.displayName="LoginModal";var components_LoginModal=LoginModal;const ModalContainer=styled_components_browser_esm.zo.div`
  position: fixed;
  z-index: 999;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;

  background: ${({theme:theme})=>theme.color.background.secondary};
`,ModalContent=styled_components_browser_esm.zo.div`
  cursor: default;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 350px;
  padding: ${({theme:theme})=>theme.space[5]};

  background-color: white;
  border-radius: 8px;
`,CloseButtonContainer=styled_components_browser_esm.zo.button`
  display: flex;
  justify-content: flex-end;

  width: 100%;
  margin-bottom: ${({theme:theme})=>theme.space[2.5]};

  color: ${({theme:theme})=>theme.color.gray};
`,CloseIcon=(0,styled_components_browser_esm.zo)(index_esm.Fk5)``,LoginTitle=styled_components_browser_esm.zo.div`
  display: flex;
  gap: ${({theme:theme})=>theme.space[1.5]};
  align-items: center;

  width: 100%;
  margin-top: ${({theme:theme})=>theme.space[2.5]};
  margin-bottom: ${({theme:theme})=>theme.space[4]};

  font-size: ${({theme:theme})=>theme.fontSize.xs};
  color: ${({theme:theme})=>theme.color.gray};

  /* ::before 스타일링 */
  &::before {
    content: '';
    flex: 1;
    height: 1px;
    background: ${({theme:theme})=>theme.color.gray};
  }

  /* ::after 스타일링 */
  &::after {
    content: '';
    flex: 1;
    height: 1px;
    background: ${({theme:theme})=>theme.color.gray};
  }
`,ButtonContainer=styled_components_browser_esm.zo.section`
  display: flex;
  justify-content: space-evenly;
  width: 100%;
`,Logo=styled_components_browser_esm.zo.img.attrs({src:Resource.Z.getAssetUrl({filename:"logo.svg"})})`
  height: ${({theme:theme})=>theme.fontSize["5xl"]};
`;try{LoginModal.displayName="LoginModal",LoginModal.__docgenInfo={description:"",displayName:"LoginModal",props:{onClose:{defaultValue:null,description:"",name:"onClose",required:!0,type:{name:"() => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/LoginModal.tsx#LoginModal"]={docgenInfo:LoginModal.__docgenInfo,name:"LoginModal",path:"src/components/LoginModal.tsx#LoginModal"})}catch(__react_docgen_typescript_loader_error){}},"./src/hooks/useSuspenseQuery.ts":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var _tanstack_react_query__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useQuery.mjs");__webpack_exports__.Z=function useSuspenseQuery(options){const queryResult=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_0__.a)({...options,suspense:!0});return{...queryResult,isIdle:"isIdle"in queryResult&&queryResult.isIdle}}},"./src/utils/Resource.ts":function(__unused_webpack_module,__webpack_exports__){class Resource{constructor(){}static getImageUrl(options){const{filename:filename,size:size}=options;return Resource.joinPath("/images",size,filename)}static getAssetUrl(options){const{filename:filename}=options;return Resource.joinPath("/assets",filename)}static joinPath(...paths){return paths.map(((part,index)=>(index>0&&(part=part.replace(/^\/+/,"")),index!==paths.length-1&&(part=part.replace(/\/+$/,"")),part))).join("/")}}__webpack_exports__.Z=Resource},"./node_modules/react-icons/lib/esm/index.js":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.d(__webpack_exports__,{w_:function(){return GenIcon}});var react=__webpack_require__("./node_modules/react/index.js"),DefaultContext={color:void 0,size:void 0,className:void 0,style:void 0,attr:void 0},IconContext=react.createContext&&react.createContext(DefaultContext),__assign=function(){return __assign=Object.assign||function(t){for(var s,i=1,n=arguments.length;i<n;i++)for(var p in s=arguments[i])Object.prototype.hasOwnProperty.call(s,p)&&(t[p]=s[p]);return t},__assign.apply(this,arguments)},__rest=function(s,e){var t={};for(var p in s)Object.prototype.hasOwnProperty.call(s,p)&&e.indexOf(p)<0&&(t[p]=s[p]);if(null!=s&&"function"==typeof Object.getOwnPropertySymbols){var i=0;for(p=Object.getOwnPropertySymbols(s);i<p.length;i++)e.indexOf(p[i])<0&&Object.prototype.propertyIsEnumerable.call(s,p[i])&&(t[p[i]]=s[p[i]])}return t};function Tree2Element(tree){return tree&&tree.map((function(node,i){return react.createElement(node.tag,__assign({key:i},node.attr),Tree2Element(node.child))}))}function GenIcon(data){return function(props){return react.createElement(IconBase,__assign({attr:__assign({},data.attr)},props),Tree2Element(data.child))}}function IconBase(props){var elem=function(conf){var className,attr=props.attr,size=props.size,title=props.title,svgProps=__rest(props,["attr","size","title"]),computedSize=size||conf.size||"1em";return conf.className&&(className=conf.className),props.className&&(className=(className?className+" ":"")+props.className),react.createElement("svg",__assign({stroke:"currentColor",fill:"currentColor",strokeWidth:"0"},conf.attr,attr,svgProps,{className:className,style:__assign(__assign({color:props.color||conf.color},conf.style),props.style),height:computedSize,width:computedSize,xmlns:"http://www.w3.org/2000/svg"}),title&&react.createElement("title",null,title),props.children)};return void 0!==IconContext?react.createElement(IconContext.Consumer,null,(function(conf){return elem(conf)})):elem(DefaultContext)}}}]);
//# sourceMappingURL=components-LoginModal-stories.c4137c8d.iframe.bundle.js.map