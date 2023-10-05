"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[425],{"./src/components/Navbar.stories.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:function(){return Default},__namedExportsOrder:function(){return __namedExportsOrder},default:function(){return Navbar_stories}});var react=__webpack_require__("./node_modules/react/index.js"),index_esm=__webpack_require__("./node_modules/react-icons/fa/index.esm.js"),fa6_index_esm=__webpack_require__("./node_modules/react-icons/fa6/index.esm.js"),dist=__webpack_require__("./node_modules/react-router-dom/dist/index.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),useUser=__webpack_require__("./src/hooks/useUser.ts"),Button=__webpack_require__("./src/components/Button.tsx"),LoginModal=__webpack_require__("./src/components/LoginModal.tsx"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const Navbar=()=>{const{data:user}=(0,useUser.Z)(),[isLoginModalOpen,setIsLoginModalOpen]=(0,react.useState)(!1);return(0,jsx_runtime.jsxs)(Container,{children:[(0,jsx_runtime.jsx)(dist.rU,{to:"/",children:(0,jsx_runtime.jsx)(Logo,{})}),(0,jsx_runtime.jsxs)(ButtonContainer,{children:[(0,jsx_runtime.jsx)(dist.rU,{to:"/rank",children:(0,jsx_runtime.jsx)(IconButton,{children:(0,jsx_runtime.jsx)(fa6_index_esm.nJJ,{})})}),(0,jsx_runtime.jsx)(dist.rU,{to:"/search",children:(0,jsx_runtime.jsx)(IconButton,{children:(0,jsx_runtime.jsx)(index_esm.U41,{})})}),(0,jsx_runtime.jsx)(UserButtonContainer,{children:user?(0,jsx_runtime.jsx)(dist.rU,{to:"/my-profile",children:(0,jsx_runtime.jsx)(Button.Z,{$variant:"outlined",$fullWidth:!0,$fullHeight:!0,children:"프로필"})}):(0,jsx_runtime.jsx)(Button.Z,{$fullWidth:!0,$fullHeight:!0,onClick:()=>{setIsLoginModalOpen(!0)},"aria-haspopup":"dialog",children:"로그인"})})]}),(0,jsx_runtime.jsx)(react.Suspense,{children:isLoginModalOpen&&(0,jsx_runtime.jsx)(LoginModal.Z,{onClose:()=>{setIsLoginModalOpen(!1)}})})]})};Navbar.displayName="Navbar";var components_Navbar=Navbar;const Container=styled_components_browser_esm.zo.nav`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 66px;
  padding: 0 ${({theme:theme})=>theme.space[4]};
`,ButtonContainer=styled_components_browser_esm.zo.div`
  display: flex;
  gap: ${({theme:theme})=>theme.space[2]};
  align-items: center;
  margin-left: auto;
`,Logo=styled_components_browser_esm.zo.img.attrs({src:"/assets/logo.svg"})`
  height: ${({theme:theme})=>theme.fontSize["3xl"]};
`,UserButtonContainer=styled_components_browser_esm.zo.div`
  width: 100px;
`,IconButton=styled_components_browser_esm.zo.button`
  cursor: pointer;

  display: flex;
  align-items: center;

  padding: ${({theme:theme})=>theme.space[2]};

  font-size: ${({theme:theme})=>theme.fontSize["2xl"]};
  color: ${({theme:theme})=>theme.color.primary};
`;var Navbar_stories={title:"Navbar",component:components_Navbar};const Default={args:{}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {}\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]},"./src/components/Button.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var styled_components__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Button=props=>{const{children:children,$variant:$variant="default",$fullWidth:$fullWidth=!1,$fullHeight:$fullHeight=!1,...rest}=props;return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(Container,{$variant:$variant,$fullWidth:$fullWidth,$fullHeight:$fullHeight,...rest,children:children})};Button.displayName="Button",__webpack_exports__.Z=Button;const ButtonVariants={secondary:styled_components__WEBPACK_IMPORTED_MODULE_1__.iv`
    color: ${props=>props.theme.color.white};
    background-color: ${props=>props.theme.color.secondary};
    border: none;
  `,outlined:styled_components__WEBPACK_IMPORTED_MODULE_1__.iv`
    color: ${props=>props.theme.color.gray};
    background-color: ${props=>props.theme.color.white};
    border: 2px solid ${props=>props.theme.color.primary};
  `,default:styled_components__WEBPACK_IMPORTED_MODULE_1__.iv`
    color: ${props=>props.theme.color.white};
    background-color: ${props=>props.theme.color.primary};
    border: none;
  `},Container=styled_components__WEBPACK_IMPORTED_MODULE_1__.ZP.button`
  padding: ${({theme:theme})=>theme.space[1.5]} ${({theme:theme})=>theme.space[2]};
  font-size: 16px;
  font-weight: 500;
  border-radius: 40px;
  ${props=>ButtonVariants[props.$variant||"default"]}
  ${props=>props.$fullWidth&&"width: 100%;"}
  ${props=>props.$fullHeight&&"height: 100%;"}
`;try{Button.displayName="Button",Button.__docgenInfo={description:"",displayName:"Button",props:{$variant:{defaultValue:null,description:"",name:"$variant",required:!1,type:{name:"enum",value:[{value:'"secondary"'},{value:'"default"'},{value:'"outlined"'}]}},$fullWidth:{defaultValue:null,description:"",name:"$fullWidth",required:!1,type:{name:"boolean"}},$fullHeight:{defaultValue:null,description:"",name:"$fullHeight",required:!1,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Button.tsx#Button"]={docgenInfo:Button.__docgenInfo,name:"Button",path:"src/components/Button.tsx#Button"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/LoginButton.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_styles_theme__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/styles/theme.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const LoginButton=props=>{const{children:children,...rest}=props;return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(Container,{...rest,children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(ButtonContent,{children:children})})};LoginButton.displayName="LoginButton",__webpack_exports__.Z=LoginButton;const Container=styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.button`
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
`;try{LoginModal.displayName="LoginModal",LoginModal.__docgenInfo={description:"",displayName:"LoginModal",props:{onClose:{defaultValue:null,description:"",name:"onClose",required:!0,type:{name:"() => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/LoginModal.tsx#LoginModal"]={docgenInfo:LoginModal.__docgenInfo,name:"LoginModal",path:"src/components/LoginModal.tsx#LoginModal"})}catch(__react_docgen_typescript_loader_error){}},"./src/hooks/useSuspenseQuery.ts":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var _tanstack_react_query__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useQuery.mjs");__webpack_exports__.Z=function useSuspenseQuery(options){const queryResult=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_0__.a)({...options,suspense:!0});return{...queryResult,isIdle:"isIdle"in queryResult&&queryResult.isIdle}}},"./src/hooks/useUser.ts":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.d(__webpack_exports__,{Z:function(){return hooks_useUser}});var client=__webpack_require__("./src/client.ts"),useMutation=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useMutation.mjs"),react=__webpack_require__("./node_modules/react/index.js"),AuthContext=__webpack_require__("./src/context/AuthContext.tsx");var hooks_useAuth=()=>{const authContext=(0,react.useContext)(AuthContext.Z);if(!authContext)throw new Error("AuthProvider를 사용해주셔야 합니다.");const{accessToken:accessToken,setAccessToken:setAccessToken,identity:identity}=authContext,{mutate:issueAccessToken}=(0,useMutation.D)({mutationFn:({provider:provider,code:code})=>client.Z.issueAccessToken(provider,code),onSettled:accessToken=>{setAccessToken(accessToken??null)}}),{mutate:clearRefreshToken}=(0,useMutation.D)({mutationFn:()=>client.Z.clearRefreshToken()});return{accessToken:accessToken,issueAccessToken:issueAccessToken,clearAuthorization:(0,react.useCallback)((()=>(setAccessToken(null),clearRefreshToken())),[clearRefreshToken]),identity:identity}},useSuspenseQuery=__webpack_require__("./src/hooks/useSuspenseQuery.ts");var hooks_useUser=()=>{const{identity:identity}=hooks_useAuth();return(0,useSuspenseQuery.Z)({queryKey:["user",identity],queryFn:()=>client.Z.getUser(identity?.sub),enabled:!!identity})}},"./src/utils/Resource.ts":function(__unused_webpack_module,__webpack_exports__){class Resource{constructor(){}static getImageUrl(options){const{filename:filename,size:size}=options;return Resource.joinPath("/images",size,filename)}static getAssetUrl(options){const{filename:filename}=options;return Resource.joinPath("/assets",filename)}static joinPath(...paths){return paths.map(((part,index)=>(index>0&&(part=part.replace(/^\/+/,"")),index!==paths.length-1&&(part=part.replace(/\/+$/,"")),part))).join("/")}}__webpack_exports__.Z=Resource}}]);
//# sourceMappingURL=components-Navbar-stories.65d570a3.iframe.bundle.js.map