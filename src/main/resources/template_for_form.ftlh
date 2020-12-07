<form method="${method}" action="${action}">
    <#list inputs as inp>
        <input type="${inp.type}" name="${inp.name}" placeholder="${inp.placeholder}">
    </#list>
</form>