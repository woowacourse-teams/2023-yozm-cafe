"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[166],{"./src/components/CafeInfoModal.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:()=>Default,__namedExportsOrder:()=>__namedExportsOrder,default:()=>CafeInfoModal_stories});var react=__webpack_require__("./node_modules/react/index.js"),index_esm=__webpack_require__("./node_modules/react-icons/sl/index.esm.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const CafeInfoModal=({title,address,content})=>{const[isOpen,setIsOpen]=(0,react.useState)(!1);return(0,jsx_runtime.jsx)(Container,{children:(0,jsx_runtime.jsxs)(Modal,{className:isOpen?"active":"",onClick:()=>setIsOpen(!isOpen),children:[(0,jsx_runtime.jsx)(Title,{children:title}),(0,jsx_runtime.jsx)(Address,{children:address}),!isOpen&&(0,jsx_runtime.jsx)(ArrowDownContainer,{children:(0,jsx_runtime.jsx)(StyledArrowDown,{})}),(0,jsx_runtime.jsx)(Content,{children:content})]})})};CafeInfoModal.displayName="CafeInfoModal";const components_CafeInfoModal=CafeInfoModal,Container=styled_components_browser_esm.zo.div`
  height: 120px;
  max-height: 120px;
`,StyledArrowDown=(0,styled_components_browser_esm.zo)(index_esm.uaK)`
  font-size: ${({theme})=>theme.fontSize["2xl"]};
  color: ${({theme})=>theme.color.line.secondary};
`,ArrowDownContainer=styled_components_browser_esm.zo.div`
  display: flex;
  justify-content: center;
`,Title=styled_components_browser_esm.zo.h1`
  font-size: ${({theme})=>theme.fontSize["3xl"]};
  font-weight: bolder;
`,Address=styled_components_browser_esm.zo.h2`
  font-size: ${({theme})=>theme.fontSize.lg};
`,Content=styled_components_browser_esm.zo.p`
  margin-top: 20px;
  color: ${({theme})=>theme.color.gray};
`,Modal=styled_components_browser_esm.zo.main`
  will-change: transform;

  position: absolute;
  bottom: 0;

  display: flex;
  flex-direction: column;
  gap: 8px;

  width: 100%;
  height: 120px;
  padding: 16px;

  opacity: 0.8;
  background: ${({theme})=>theme.color.background.primary};
  backdrop-filter: blur(20px);
  border-radius: 20px;

  transition: all 0.2s;

  &:not(.active):hover {
    cursor: pointer;
    transform: scale(1.02) translateY(-4px);
  }

  &.active {
    right: 0;
    bottom: 0;
    left: 0;

    height: 500px;

    opacity: 1;
    background: ${({theme})=>theme.color.white};
    backdrop-filter: none;
  }

  & > ${Content} {
    opacity: 0;
    transition: opacity 0.2s;
  }

  &.active > ${Content} {
    opacity: 1;
  }
`;try{CafeInfoModal.displayName="CafeInfoModal",CafeInfoModal.__docgenInfo={description:"",displayName:"CafeInfoModal",props:{title:{defaultValue:null,description:"",name:"title",required:!0,type:{name:"string"}},address:{defaultValue:null,description:"",name:"address",required:!0,type:{name:"string"}},content:{defaultValue:null,description:"",name:"content",required:!0,type:{name:"string"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/CafeInfoModal.tsx#CafeInfoModal"]={docgenInfo:CafeInfoModal.__docgenInfo,name:"CafeInfoModal",path:"src/components/CafeInfoModal.tsx#CafeInfoModal"})}catch(__react_docgen_typescript_loader_error){}const CafeInfoModal_stories={title:"CafeInfoModal",component:components_CafeInfoModal},Default={args:{title:"성수동 카페",address:"서울 성동구 연무장3길 6",content:"안녕하세요~!!!!!"}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    title: '성수동 카페',\n    address: '서울 성동구 연무장3길 6',\n    content: '안녕하세요~!!!!!'\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]}}]);
//# sourceMappingURL=components-CafeInfoModal-stories.ebcfd583.iframe.bundle.js.map