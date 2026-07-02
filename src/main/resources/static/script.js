async function shortenUrl(){

    const url=document.getElementById("urlInput").value;

    const result=document.getElementById("result");

    result.innerHTML='<div class="loader"></div>';

    try{

        const response=await fetch("/api/shorten",{

            method:"POST",

            headers:{
                "Content-Type":"application/json"
            },

            body:JSON.stringify({
                originalUrl:url
            })

        });

        const data=await response.json();

        if(!response.ok){

            result.innerHTML=
            `<p class="error">${data}</p>`;

            return;

        }

        const shortUrl=window.location.origin+"/r/"+data.shortCode;

        result.innerHTML=`

        <p class="success">URL Shortened Successfully 🎉</p>

        <br>

        <a href="${shortUrl}" target="_blank">

        ${shortUrl}

        </a>

        <br><br>

        <button onclick="copyLink('${shortUrl}')">

        Copy Link

        </button>

        `;

    }

    catch(e){

        result.innerHTML=
        `<p class="error">Something went wrong.</p>`;

    }

}

function copyLink(link){

    navigator.clipboard.writeText(link);

    alert("Copied to clipboard!");

}