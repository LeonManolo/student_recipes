"use client";
export default function CreateIngedrient() {
    return (
        <div className="justify-center">
            <button onClick={() => createIngedrients()} className="btn btn-outline btn-accent">Button</button>
            {/* The button to open modal */}
            <a href="#my-modal-2" className="btn">open modal</a>
            <div className="modal" id="my-modal-2">
                <div className="modal-box">
                    <h3 className="font-bold text-lg">Congratulations random Internet user!</h3>
                    <p className="py-4">Youve been selected for a chance to get one year of subscription to use Wikipedia for free!</p>
                    <div className="modal-action">
                        <a href="#" className="btn">Yay!</a>
                    </div>
                </div>
            </div>
        </div >
    );

    async function createIngedrients(): Promise<void> {
        const json = JSON.stringify({
            "locale": "de",
            "title": "Gurke",
            "calories": 135,
            "protein": 22,
            "carbohydrates": 12,
            "fat": 33
        });
        try {
            const result = await fetch("https://sea-turtle-app-hqisk.ondigitalocean.app/api/ingredients", {
                method: "POST", body: json, mode: 'no-cors', headers: {
                    'Content-Type': 'application/json'
                    // 'Content-Type': 'application/x-www-form-urlencoded',
                },
            }); // cache no store um bei jedem Seiten aufruf die Rezepte zu fetchen
            console.log(result.status)
            console.log("Moin")
        } catch (e: any) {
            console.log(e?.message)
        }
    }
}