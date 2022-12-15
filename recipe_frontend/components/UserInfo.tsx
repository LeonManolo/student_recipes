/* Component to render all information provided by user on registration. */
export default function CreateAvatar({ children, ...props }: any) {
  return (
    <div className="stats stats-vertical shadow">
      <div className="stat">
        <div className="stat-title">Vorname: </div>
        <div className="stat-value">firstName</div>
        {/*                 <div className="stat-desc">Jan 1st - Feb 1st</div> */}
      </div>

      <div className="stat">
        <div className="stat-title">Nachname: </div>
        <div className="stat-value">lastName</div>
      </div>

      <div className="stat">
        <div className="stat-title">E-Mail: </div>
        <div className="stat-value">hans@mustermann.de</div>
      </div>
    </div>
  );
}
