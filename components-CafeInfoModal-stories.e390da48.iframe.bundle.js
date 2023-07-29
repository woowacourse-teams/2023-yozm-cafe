"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[166],{"./src/components/CafeInfoModal.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:()=>Default,__namedExportsOrder:()=>__namedExportsOrder,default:()=>CafeInfoModal_stories});var react=__webpack_require__("./node_modules/react/index.js"),index_esm=__webpack_require__("./node_modules/react-icons/bi/index.esm.js"),sl_index_esm=__webpack_require__("./node_modules/react-icons/sl/index.esm.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const CafeInfoModal=({title,address,content})=>{const[isOpen,setIsOpen]=(0,react.useState)(!1);return(0,jsx_runtime.jsxs)(Container,{children:[(0,jsx_runtime.jsx)(Title,{children:title}),(0,jsx_runtime.jsxs)(Address,{children:[(0,jsx_runtime.jsx)(LocationPinIcon,{}),address]}),(0,jsx_runtime.jsxs)(Modal,{className:isOpen?"active":"",onClick:()=>setIsOpen(!isOpen),children:[!isOpen&&(0,jsx_runtime.jsx)(MoreInfoContainer,{children:(0,jsx_runtime.jsxs)(MoreInfoContent,{children:[(0,jsx_runtime.jsx)(SolidInfoCircleIcon,{}),(0,jsx_runtime.jsx)(Text,{children:"더보기"})]})}),(0,jsx_runtime.jsxs)(Content,{children:[title,content]})]})]})};CafeInfoModal.displayName="CafeInfoModal";const components_CafeInfoModal=CafeInfoModal,Container=styled_components_browser_esm.zo.div`
  overflow: hidden;
  display: flex;
  flex-direction: column;

  height: 80px;
  max-height: 80px;
`,Text=styled_components_browser_esm.zo.span`
  font-size: ${({theme})=>theme.fontSize.sm};
  color: ${({theme})=>theme.color.white};
`,MoreInfoContent=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`,MoreInfoContainer=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  padding-right: ${({theme})=>theme.space[5]};
`,SolidInfoCircleIcon=(0,styled_components_browser_esm.zo)(index_esm.CPX)`
  font-size: ${({theme})=>theme.fontSize["4xl"]};
  color: ${({theme})=>theme.color.white};
`,LocationPinIcon=(0,styled_components_browser_esm.zo)(sl_index_esm.OxZ)`
  margin-right: ${({theme})=>theme.space[1]};
  font-size: ${({theme})=>theme.fontSize.sm};
`,Title=styled_components_browser_esm.zo.h1`
  padding-bottom: ${({theme})=>theme.space[1.5]};
  padding-left: ${({theme})=>theme.space[3]};

  font-size: ${({theme})=>theme.fontSize["3xl"]};
  font-weight: bolder;
  color: ${({theme})=>theme.color.white};
`,Address=styled_components_browser_esm.zo.h2`
  padding-left: ${({theme})=>theme.space[3]};
  font-size: ${({theme})=>theme.fontSize.lg};
  color: ${({theme})=>theme.color.white};
`,Content=styled_components_browser_esm.zo.p`
  margin-top: ${({theme})=>theme.space[5]};
  color: ${({theme})=>theme.color.gray};
`,Modal=styled_components_browser_esm.zo.main`
  position: absolute;
  width: 100%;

  &:not(.active) {
    cursor: pointer;
    height: 80px;
  }
  &.active {
    cursor: pointer;

    right: 0;
    bottom: 0;
    left: 0;

    height: 500px;

    opacity: 1;
    background: ${({theme})=>theme.color.white};
    backdrop-filter: none;
    border-radius: 20px 20px 0 0;
  }

  & > ${Content} {
    opacity: 0;
  }

  &.active > ${Content} {
    opacity: 1;
  }
`;try{CafeInfoModal.displayName="CafeInfoModal",CafeInfoModal.__docgenInfo={description:"",displayName:"CafeInfoModal",props:{title:{defaultValue:null,description:"",name:"title",required:!0,type:{name:"string"}},address:{defaultValue:null,description:"",name:"address",required:!0,type:{name:"string"}},content:{defaultValue:null,description:"",name:"content",required:!0,type:{name:"string"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/CafeInfoModal.tsx#CafeInfoModal"]={docgenInfo:CafeInfoModal.__docgenInfo,name:"CafeInfoModal",path:"src/components/CafeInfoModal.tsx#CafeInfoModal"})}catch(__react_docgen_typescript_loader_error){}const CafeInfoModal_stories={title:"CafeInfoModal",component:components_CafeInfoModal},Default={args:{title:"성수동 카페",address:"서울 성동구 연무장3길 6",content:"안녕하세요~!!!!!"}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    title: '성수동 카페',\n    address: '서울 성동구 연무장3길 6',\n    content: '안녕하세요~!!!!!'\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]}}]);
//# sourceMappingURL=components-CafeInfoModal-stories.e390da48.iframe.bundle.js.map